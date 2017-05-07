from Testing import testPenData
from Testing import testCarData
from Testing import stDeviation
from Testing import average

def main():
	count = 0
	while count <= 40:
		output1 = []
		output2 = []
		for num in range(5):
			output1.append(testCarData([count])[1])
			output2.append(testPenData([count])[1])

		print "max1", max(output1)
		print "stddev1", stDeviation(output1)
		print "average1", average(output1)

		print "max2", max(output2)
		print "stddev2", stDeviation(output2)
		print "average2", average(output2)

		count += 5

if __name__== '__main__':
	main()