import os

INPUT_PATH = "../inputs/"
X = 2020


def problem_input(filename):
    path = os.path.join(INPUT_PATH, filename)
    inputs = []
    with open(path, 'r') as f:
        for n in f:
            inputs.append(int(n))

    return inputs


def find_pair(inpt):
    sum_map = {}
    for n in inpt:
        if n in sum_map.keys():
            return [n, sum_map[n]]
        diff = X - n
        sum_map[diff] = n

    return []

def find_triplet(inpt):
    sum_map = {}
    for i in range(len(inpt)-1):
        for j in range(i+1, len(inpt)):
            x, y = inpt[i], inpt[j]
            diff = X - (x + y)
            if y in sum_map.keys():
                match = sum_map[y]
                match.append(y)
                return match
            sum_map[diff] = [x, y]
    return []

def solution_part_1(inpt):
    """Given a list of integers, find two numbers whose sum is certain X value.
    Find thex product of such numbers.
    Here, the given value for X is 2020.
    Edge cases:
    1. No such numbers exist -> 0
    2. Multiple such numbers -> Take the first two numbers that sum upto X"""
    pair = find_pair(inpt)
    if len(pair) == 0:
        return 0
    else:
        x, y = pair
        return x * y


def solution_part_2(inpt):
    """Given a list of integers, find three numbers whose sum is certain X value.
    Find thex product of such numbers.
    Here, the given value for X is 2020.
    Edge cases:
    1. No such numbers exist -> 0
    2. Multiple such numbers -> Take the first three numbers that sum upto X"""
    pair = find_triplet(inpt)
    if len(pair) == 0:
        return 0
    else:
        x, y, z = pair
        return x * y * z



if __name__ == "__main__":
    part_1_inpt = problem_input("day-1-part-1.input")
    ans_1 = solution_part_1(part_1_inpt)
    print(f'Answer Part 1: {ans_1}')

    part_2_inpt = problem_input("day-1-part-1.input")
    ans_2 = solution_part_2(part_2_inpt)
    print(f'Answer Part 2: {ans_2}')
