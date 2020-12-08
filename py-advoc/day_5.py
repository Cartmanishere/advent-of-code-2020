import os

INPUT_PATH = "../inputs/"
TRANS_TABLE = str.maketrans('fbrl', '0110')


def problem_input(filename):
    path = os.path.join(INPUT_PATH, filename)
    inputs = []
    with open(path, 'r') as f:
        for n in f:
            inputs.append(n)

    return inputs


def get_seat_id(code):
    """
    For a given `code` return the corresponding seat id. The logic for
    this is as follows:
    The first 7 chars represent binary row (R)
    The remaining 3 chars represent binary column (C)
    Seat is defined as (R * 8) + C
    """
    bin_code = code.lower().translate(TRANS_TABLE)
    row, col = int(bin_code[:7], 2), int(bin_code[7:], 2)
    return (row * 8) + col


def solution_part_1(inpt):
    return max(map(get_seat_id, inpt))


def solution_part_2(inpt):
    ids = list(map(get_seat_id, inpt))
    ids.sort()
    prev = ids[0]
    for cur in ids[1:]:
        if (prev + 1) != cur:
            return prev + 1
        prev = cur
    return -1


if __name__ == "__main__":
    inpt = problem_input("day-5-part-1.input")
    ans = solution_part_1(inpt)
    print(f'Answer: {ans}')

    ans = solution_part_2(inpt)
    print(f'Answer: {ans}')
