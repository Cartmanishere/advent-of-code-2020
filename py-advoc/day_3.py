import os

INPUT_PATH = "../inputs/"
TREE = '#'
PART_2_SLOPES = [(1, 1), (3, 1), (5, 1), (7, 1), (1, 2)]
PART_1_SLOPE = (3, 1)

def problem_input(filename):
    path = os.path.join(INPUT_PATH, filename)
    inputs = []
    with open(path, 'r') as f:
        for line in f:
            inputs.append(list(line.replace('\n', '')))

    return inputs


def match_slope(cur_row, slope_map, field):
    for slope in slope_map.keys():
        right, down = slope
        if cur_row == 0:
            # Assuming that no slope will have a match
            # for first row, otherwise we go into infinite
            # traversal.
            continue

        if cur_row % down != 0:
            continue

        steps = cur_row // down
        right_idx = (steps * right) % len(field[0])
        if field[cur_row][right_idx] == TREE:
            slope_map[slope] += 1

    return slope_map


def count_trees(field, slope_map):
    for row_idx in (range(len(field))):
        slope_map = match_slope(row_idx, slope_map, field)

    return slope_map


def solution_part_1(field):
    slope_map = {PART_1_SLOPE: 0}
    slope_map = count_trees(field, slope_map)
    return slope_map[PART_1_SLOPE]

def solution_part_2(field):
    slope_map = {slope: 0 for slope in PART_2_SLOPES}
    slope_map = count_trees(field, slope_map)
    ans = 1
    for count in slope_map.values():
        ans *= count
    return ans


if __name__ == "__main__":
    field = problem_input("day-3-part-1.input")
    trees = solution_part_1(field)
    print(f'Answer for Part 1: {trees}')

    ans = solution_part_2(field)
    print(f'Answer for Part 2: {ans}')
