from Testing import testPenData
from Testing import testCarData
from Testing import stDeviation
from Testing import average

def main():
	for count in range(5):
		output1 = []
		output1.append(testCarData()[1])
		print "accuracy1", testCarData()[1]
		print "max", max(output1)
		print "stddev", stDeviation(output1)
		print "average", average(output1)

		output2 = []
		output2.append(testPenData()[1])
		print "accuracy2", testPenData()[1]
		print "max", max(output2)
		print "stddev", stDeviation(output2)
		print "average", average(output2)

if __name__== '__main__':
	main()