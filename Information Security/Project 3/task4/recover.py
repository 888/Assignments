#!/usr/bin/python
import json, sys, hashlib

def usage():
    print """Usage:
    python recover.py student_id (i.e., qchenxiong3)"""
    sys.exit(1)

#TODO
def recover_msg(N1, N2, N3, C1, C2, C3):

    m = 42
    # your code starts here: to calculate the original message - m
    # Note 'm' should be an integer

    # your code ends here
    n = [N1, N2, N3]
    c = [C1, C2, C3]
    cr = chinese_remainder(n, c)
    m = find_invpow(cr, 3)
    # convert the int to message string
    msg = hex(m).rstrip('L')[2:].decode('hex')
    return msg

def chinese_remainder(n, a):
    sum = 0
    prod = reduce(lambda a, b: a*b, n)

    for n_i, a_i in zip(n, a):
        p = prod / n_i
        sum += a_i * mul_inv(p, n_i) * p
    return sum % prod

def mul_inv(a, b):
    b0 = b
    x0, x1 = 0, 1
    if b == 1: return 1
    while a > 1:
        q = a / b
        a, b = b, a%b
        x0, x1 = x1 - q * x0, x0
    if x1 < 0: x1 += b0
    return x1

def find_invpow(x,n):
    high = 1
    while high ** n <= x:
        high *= 2
    low = high/2
    while low < high:
        mid = (low + high) // 2
        if low < mid and mid**n < x:
            low = mid
        elif high > mid and mid**n > x:
            high = mid
        else:
            return mid
    return mid + 1

def main():
    if len(sys.argv) != 2:
        usage()

    all_keys = None
    with open('keys4student.json', 'r') as f:
        all_keys = json.load(f)

    name = hashlib.sha224(sys.argv[1].strip()).hexdigest()
    if name not in all_keys:
        print sys.argv[1], "not in keylist"
        usage()

    data = all_keys[name]
    N1 = int(data['N0'], 16)
    N2 = int(data['N1'], 16)
    N3 = int(data['N2'], 16)
    C1 = int(data['C0'], 16)
    C2 = int(data['C1'], 16)
    C3 = int(data['C2'], 16)

    msg = recover_msg(N1, N2, N3, C1, C2, C3)
    print msg

if __name__ == "__main__":
    main()
