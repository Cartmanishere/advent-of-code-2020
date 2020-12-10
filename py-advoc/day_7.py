import os
import re
from functools import lru_cache

INPUT_PATH = "../inputs/"


def problem_input(filename):
    path = os.path.join(INPUT_PATH, filename)
    inpt = []
    with open(path, 'r') as f:
        for line in f:
            inpt.append(line)
    return inpt


def parse_contents(contents):
    if re.search('no other', contents):
        return []

    cc = [re.sub('bag(s)?', '', i).strip() for i in contents.split(',')]
    rules = []
    for i in cc:
        n, desc, color = i.split()[:3]
        rules.append([int(n), desc + " " + color])
    return rules



def conv_line_rule(line):
    bag, contents = [i.strip() for i in line.split('contain')]
    bag_name = bag.replace('bags', '').strip()
    bag_contents = parse_contents(contents)
    return {bag_name: bag_contents}


def construct_rules(lines):
    rules_map = {}
    for line in lines:
        rule = conv_line_rule(line)
        rules_map = {**rules_map, **rule}
    return rules_map

#  ───────────────────────────── PART 1 ─────────────────────────────
cache_rule_map = {}

@lru_cache(maxsize=1000)
def cached_can_contain_gold(bag_name):
    contents = cache_rule_map[bag_name]
    if len(contents) == 0:
        return False

    ans = False
    for n, bag in contents:
        ans = ans or bag == 'shiny gold' or cached_can_contain_gold(bag)
    return ans

def solution_part_1_cached(inpt):
    global cache_rule_map
    cache_rule_map = construct_rules(inpt)
    count = 0
    for bag_name in cache_rule_map.keys():
        if cached_can_contain_gold(bag_name):
            count += 1
    return count

# Memoized solution timing stats
# 10.4 ms ± 80.8 µs per loop (mean ± std. dev. of 7 runs, 100 loops each)

def can_contain_gold(rules_map, bag_name):
    """
    Check whether according to given rules, whether a certain bag
    can contain a gold bag or not.
    """
    contents = rules_map[bag_name]
    if len(contents) == 0:
        return False

    ans = False
    for n, bag in contents:
        ans = ans or bag == 'shiny gold' or can_contain_gold(rules_map, bag)
    return ans

def solution_part_1(inpt):
    rules_map = construct_rules(inpt)
    count = 0
    for bag_name in rules_map.keys():
        if can_contain_gold(rules_map, bag_name):
            count += 1
    return count

# Non-memoized solution timing stats
# 70.1 ms ± 1.29 ms per loop (mean ± std. dev. of 7 runs, 10 loops each)

#  ───────────────────────────── PART 2 ─────────────────────────────

cache_rule_map = {}
@lru_cache(maxsize=1000)
def count_bags(bag_name):
    contents = cache_rule_map[bag_name]
    if len(contents) == 0:
        return 0

    count = 0
    for n, bag in contents:
        count = count + n + (n * count_bags(bag))
    return count

def solution_part_2(inpt):
    global cache_rule_map
    cache_rule_map = construct_rules(inpt)
    return count_bags('shiny gold')
