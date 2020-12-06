import os
import re

INPUT_PATH = "../inputs/"
REQ_KEYS = {'byr', 'iyr', 'eyr', 'hgt', 'hcl', 'ecl', 'pid'}

def parse_input(contents):
    blocks = [i.replace('\n', ' ').strip() for i in contents.split('\n\n')]
    pass_maps = []
    for block in blocks:
        kmap = {key:val for key, val \
                in map(lambda x: x.split(':'), block.split(" "))}
        pass_maps.append(kmap)

    return pass_maps


def problem_input(filename):
    path = os.path.join(INPUT_PATH, filename)
    with open(path, 'r') as f:
        contents = f.read()
    return parse_input(contents)

#  ───────────────────────────── PART 1 ─────────────────────────────

def valid_passport_part_1(pmap):
    """
    Check whether a given password map is a valid password.
    The condition for validity is that REQ_KEYS should be present in
    the passport map.
    """
    ks = set(pmap.keys())
    return len(REQ_KEYS - ks) == 0


def solution_part_1(inpt):
    valid_pass = filter(lambda x: valid_passport_part_1(x), inpt)
    return len(list(valid_pass))

#  ───────────────────────────── PART 2 ─────────────────────────────

def s_def(key, pred):
    return lambda x: pred(x.get(key))

def is_valid(vmap, sdefs):
    for sdef in sdefs:
        if not sdef(vmap):
            return False
    return True

def validate_year(limits):
    def validate(value):
        if value is None:
            return False
        n = int(value)
        l, u = limits
        return l <= n and n <= u
    return validate

def validate_height(cm_limits, in_limits):
    def validate(value):
        if value is None:
            return False
        n = re.search('[0-9]+', value)
        metric = re.search('[a-zA-Z]+', value)
        num = int(n.group(0)) if n else None
        metric = metric.group(0) if metric else None
        if num is None:
            return False
        elif metric is None:
            return False
        elif metric == 'in':
            l, u = in_limits
            return l <= num and num <= u
        elif metric == 'cm':
            l, u = cm_limits
            return l <= num and num <= u
        return False
    return validate

valid_eye_colors = {"amb", "blu", "brn", "gry", "grn", "hzl", "oth"}

schema = [s_def("byr", validate_year([1920, 2002])),
          s_def("iyr", validate_year([2010, 2020])),
          s_def("eyr", validate_year([2020, 2030])),
          s_def("hgt", validate_height([150, 193], [59, 76])),
          s_def("hcl", lambda value: False if value is None else \
                re.search('#[0-9a-f]{6}', value) is not None),
          s_def("ecl", lambda value: False if value is None else \
                value in valid_eye_colors),
          s_def("pid", lambda value: False if value is None else \
                re.search('^[0-9]{9}$', value) is not None)]

def valid_passport_part_2(pmap):
    return is_valid(pmap, schema)

def solution_part_2(inpt):
    count = 0
    for idx, val in enumerate(inpt):
        if valid_passport_part_2(val):
            count += 1
    return count


if __name__ == "__main__":
    input_file = "day-4-part-1.input"
    inpt = problem_input(input_file)
    ans = solution_part_1(inpt)
    print(f'Answer for Part 1: {ans}')

    ans = solution_part_2(inpt)
    print(f'Answer for Part 2: {ans}')
