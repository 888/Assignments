#include <stdio.h>
#include <string.h>
#include <unistd.h>
#include "rtp.h"

/* GIVEN Function:
 * Handles creating the client's socket and determining the correct
 * information to communicate to the remote server
 */
CONN_INFO* setup_socket(char *ip, char *port){
	struct addrinfo *connections, *conn = NULL;
	struct addrinfo info;
	memset(&info, 0, sizeof(struct addrinfo));
	int sock = 0;

	info.ai_family = AF_INET;
	info.ai_socktype = SOCK_DGRAM;
	info.ai_protocol = IPPROTO_UDP;
	getaddrinfo(ip, port, &info, &connections);

	/*for loop to determine corr addr info*/
	for(conn = connections; conn != NULL; conn = conn->ai_next){
		sock = socket(conn->ai_family, conn->ai_socktype, conn->ai_protocol);
		if(sock <0){
			if(DEBUG)
				perror("Failed to create socket\n");
			continue;
		}
		if(DEBUG)
			printf("Created a socket to use.\n");
		break;
	}
	if(conn == NULL){
		perror("Failed to find and bind a socket\n");
		return NULL;
	}
	CONN_INFO* conn_info = malloc(sizeof(CONN_INFO));
	conn_info->socket = sock;
	conn_info->remote_addr = conn->ai_addr;
	conn_info->addrlen = conn->ai_addrlen;
	return conn_info;
}

void shutdown_socket(CONN_INFO *connection){
	if(connection)
		close(connection->socket);
}

/* 
 * ===========================================================================
 *
 *			STUDENT CODE STARTS HERE. PLEASE COMPLETE ALL FIXMES
 *
 * ===========================================================================
*/


/*
 *  Returns a number computed based on the data in the buffer.
*/
static int checksum(const char *buffer, int length){

	/*  ----  FIXME  ----
	 *
	 *  The goal is to return a number that is determined by the contents
	 *  of the buffer passed in as a parameter.  There a multitude of ways
	 *  to implement this function.  For simplicity, simply sum the ascii
	 *  values of all the characters in the buffer, and return the total.
	*/
	int output = 0;
	for (int i = 0; i < length; i++) {
		int add = (int) buffer[i];
		output += add; 
	}
	return output;
}

/*
 *  Converts the given buffer into an array of PACKETs and returns
 *  the array.  The value of (*count) should be updated so that it 
 *  contains the length of the array created.
 */
static PACKET* packetize(const char *buffer, int length, int *count){

	/*  ----  FIXME  ----
	 *  The goal is to turn the buffer into an array of packets.
	 *  You should allocate the space for an array of packets and
	 *  return a pointer to the first element in that array.  Each 
	 *  packet's type should be set to DATA except the last, as it 
	 *  should be LAST_DATA type. The integer pointed to by 'count' 
	 *  should be updated to indicate the number of packets in the 
	 *  array.
	*/
	int uneven;
	int temp;	
	int test = length % MAX_PAYLOAD_LENGTH;
	if (test == 0) {
		temp = length / MAX_PAYLOAD_LENGTH;
		uneven = 0;
	} else {
		temp = length / MAX_PAYLOAD_LENGTH + 1;
		uneven = 1;
	}
	*count = temp;
	PACKET* arr = malloc(temp * sizeof(PACKET));
	for (int i = 0; i < temp - 1; i++) {
		memcpy(arr[i].payload, buffer + i*MAX_PAYLOAD_LENGTH, MAX_PAYLOAD_LENGTH);
		arr[i].type = DATA;
		arr[i].payload_length = MAX_PAYLOAD_LENGTH;
		arr[i].checksum = checksum(arr[i].payload, MAX_PAYLOAD_LENGTH);
	}
	if (uneven) {
		memcpy(arr[temp - 1].payload, buffer + (temp - 1) * MAX_PAYLOAD_LENGTH, test);
		arr[temp - 1].type = LAST_DATA;
		arr[temp - 1].payload_length = test;
		arr[temp - 1].checksum = checksum(arr[temp - 1].payload, test);
	} else {
		memcpy(arr[temp - 1].payload, buffer + (temp - 1) * MAX_PAYLOAD_LENGTH, MAX_PAYLOAD_LENGTH);
		arr[temp - 1].type = LAST_DATA;
		arr[temp - 1].payload_length = MAX_PAYLOAD_LENGTH;
		arr[temp - 1].checksum = checksum(arr[temp - 1].payload, MAX_PAYLOAD_LENGTH);
	}
	return arr;
}

/*
 * Send a message via RTP using the connection information
 * given on UDP socket functions sendto() and recvfrom()
 */
int rtp_send_message(CONN_INFO *connection, MESSAGE *msg) {
	/* ---- FIXME ----
	 * The goal of this function is to turn the message buffer
	 * into packets and then, using stop-n-wait RTP protocol,
	 * send the packets and re-send if the response is a NACK.
	 * If the response is an ACK, then you may send the next one
	*/
	int count = 0;
	int num = 0;
	PACKET* packet = packetize(msg->buffer, msg->length, &count);
	while (num < count) {
		sendto(connection->socket, &(packet[num]), sizeof(PACKET), 0, connection->remote_addr, connection->addrlen);
		PACKET* back = malloc(sizeof(PACKET));
		recvfrom(connection->socket, back, sizeof(PACKET), 0, connection->remote_addr, &connection->addrlen);
		if (back->type == ACK) {
			num++;
		}
	}
	return 0;
}

/*
 * Receive a message via RTP using the connection information
 * given on UDP socket functions sendto() and recvfrom()
 */
MESSAGE* rtp_receive_message(CONN_INFO *connection) {
	/* ---- FIXME ----
	 * The goal of this function is to handle 
	 * receiving a message from the remote server using
	 * recvfrom and the connection info given. You must 
	 * dynamically resize a buffer as you receive a packet
	 * and only add it to the message if the data is considered
	 * valid. The function should return the full message, so it
	 * must continue receiving packets and sending response 
	 * ACK/NACK packets until a LAST_DATA packet is successfully 
	 * received.
	*/
	int done = 0;
	MESSAGE* msg = malloc(sizeof(MESSAGE));
	while (!done) {
		PACKET* packet = malloc(sizeof(PACKET));
		recvfrom(connection->socket, packet, sizeof(PACKET), 0, connection->remote_addr, &connection->addrlen);
		PACKET* back = malloc(sizeof(PACKET));
		if (packet->checksum == checksum(packet->payload, packet->payload_length)) {
			if (packet->type == LAST_DATA) {
				done = 1;
			}			
			back->type = ACK;
			msg->buffer = realloc(msg->buffer, msg->length + packet->payload_length);
			memmove(msg->buffer + msg->length, packet->payload, packet->payload_length);
			msg->length += packet->payload_length;
		} else {
			back->type = NACK;
		}
		sendto(connection->socket, back, sizeof(PACKET), 0, connection->remote_addr, connection->addrlen);
	}
	return msg;
}
