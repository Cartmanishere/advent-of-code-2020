import os

INPUT_PATH = "../inputs/"


def problem_input(filename):
    path = os.path.join(INPUT_PATH, filename)
    inputs = []
    with open(path, 'r') as f:
        return f.read().split('\n\n')


def count_qs_part_1(group):
    """
    Given a list of replies by all the people in the group,
    return the count of yes replies by ANYONE in the group.
    """
    return len(set(group.replace('\n', '')))

def solution_part_1(groups):
    return sum(map(count_qs_part_1, groups))


def count_qs_part_2(group):
    """
    Given a list of replies by all the pople in the group,
    return the count of yes replies by EVERYONE in the group.
    """
    # Always beware of a trailing newline in python ( •̀ᴗ•́ )و ̑̑
    replies = group.strip('\n').split('\n')
    int_set = set(replies[0])
    for rep in replies[1:]:
        int_set = int_set.intersection(rep)

    return len(int_set)

def solution_part_2(groups):
    return sum(map(count_qs_part_2, groups))


if __name__ == "__main__":
    inpt = problem_input("day-6-part-1.input")
    ans = solution_part_1(inpt)
    print(f'Answer for Part 1: {ans}')

    ans = solution_part_2(inpt)
    print(f'Answer for Part 2: {ans}')
