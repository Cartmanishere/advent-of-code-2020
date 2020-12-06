import os
from collections import Counter

INPUT_PATH = "../inputs/"

def parse_input(line):
    nums, c, password = line.split()
    i, j = [int(i) for i in nums.split("-")]
    return [i, j, c[0], password]


def problem_input(filename):
    path = os.path.join(INPUT_PATH, filename)
    inputs = []
    with open(path, 'r') as f:
        for n in f:
            inputs.append(parse_input(n))

    return inputs


def valid_password_part_1(*args):
    min_cnt, max_cnt, char, password = args
    count = Counter(password)[char]
    return (count >= min_cnt) and (count <= max_cnt)


def solution_part_1(inpt):
    f = filter(lambda x: valid_password_part_1(*x), inpt)
    return len(list(f))


def valid_password_part_2(*args):
    i, j, char, password = args
    a, b = password[(i - 1)], password[(j - 1)]
    return (a == char and b != char) or (b == char and a != char)

def solution_part_2(inpt):
    f = filter(lambda x: valid_password_part_2(*x), inpt)
    return len(list(f))


if __name__ == "__main__":
    part_1_inpt = problem_input("day-2-part-1.input")
    ans_1 = solution_part_1(part_1_inpt)
    print(f'Answer Part 1: {ans_1}')

    part_2_inpt = problem_input("day-2-part-1.input")
    ans_2 = solution_part_2(part_2_inpt)
    print(f'Answer Part 2: {ans_2}')
