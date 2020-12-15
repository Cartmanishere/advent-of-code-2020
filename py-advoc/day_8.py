import os

INPUT_PATH = "../inputs/"

def problem_input(filename):
    path = os.path.join(INPUT_PATH, filename)
    inpt = []
    with open(path, 'r') as f:
        for line in f:
            inpt.append(line)
    return inpt


def execute(idx, prog, acc, e_ops, return_acc=False):
    if idx in e_ops:
        if return_acc:
            return acc
        return

    if idx >= len(prog):
        return acc

    op_name, op_val_str = prog[idx].split()

    if op_name == 'acc':
        acc += int(op_val_str)

    next_idx = idx + 1
    if op_name == 'jmp':
        next_idx = idx + int(op_val_str)

    e_ops.add(idx)
    return execute(next_idx, prog, acc, e_ops, return_acc=return_acc)


def main_runner(prog):
    b_check = execute(0, prog, 0, set())
    if b_check:
        return b_check

    for i in range(len(prog)):
        op_name, op_val_str = prog[i].split()
        if op_name == "acc":
            continue
        if op_name == "jmp":
            new_prog = prog[:i] + ["nop " + op_val_str] + prog[i + 1:]
        if op_name == "nop":
            new_prog = prog[:i] + ["jmp " + op_val_str] + prog[i + 1:]
        b_check = execute(0, new_prog, 0, set())
        if b_check:
            return b_check

    return

if __name__ == "__main__":

    inpt = problem_input("day-8.input")
    ans = execute(0, inpt, 0, set(), return_acc=True)
    print("Answer for Part 1: ", ans)

    ans = main_runner(inpt)
    print("Answer for Part 2: ", ans)
