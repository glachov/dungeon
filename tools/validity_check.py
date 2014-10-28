# This Python script is a validity check for the Dungeon resource files.
# Paste it into the res/ directory and run it to check for possible problems.
# Created by Bernardo Sulzbach (mafagafogigante@gmail.com) on 28/10/2014.

from os import listdir, path

# The file counter
files = 0

# The problem counter
problems = 0

# Ignored line starts
ignored_starts = ("#", "//")

print_error = lambda number, error: print("  Line {}: {}".format(number, error))


def is_comment(line):
    for string in ignored_starts:
        if line.startswith(string):
            return True
    return False


def check_for_true_or_false(line_number, line):
    if line.lower().find('true') != -1:
        # Checks if the invalid word is the first after the colon.
        if line.split(":")[1].split()[0] == 'true':
            print_error(line_number, "'true' used as a boolean.")
    if line.lower().find('false') != -1:
        # Checks if the invalid word is the first after the colon.
        if line.split(":")[1].split()[0] == 'false':
            print_error(line_number, "'false' used as a boolean.")


def check(file):
    assert hasattr(file, "read")
    number = 0
    for line in file:
        number += 1
        if len(line) != 0 and not line.isspace() and not is_comment(line):
            if line.find(":") == -1:
                print_error(number, "':' not found.")
            check_for_true_or_false(number, line)

    return 0


if __name__ == '__main__':
    for filename in listdir("."):
        # Avoid checking this script.
        if filename != path.basename(__file__):
            files += 1
            print("Checking", filename)
            problems += check(open(filename, mode='r'))
    if problems == 0:
        print("Checked", files, "files.")
    elif problems == 1:
        print("Checked", files, "files and found 1 problem.")
    else:
        print("Checked", files, "files and found", problems, "problems.")
