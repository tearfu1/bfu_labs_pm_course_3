from os import listdir, mkdir, system, path as ospath
from sys import argv
from shutil import copy2

try:
    path = argv[1]
except:
    path = '.'

folderInsides = listdir(path)
smallFiles = []
for inside in folderInsides:
    pathToFile = f'{path}/{inside}'
    if 0 < ospath.getsize(pathToFile) < 2000:
        smallFiles.append(inside)

if len(smallFiles):
    print(smallFiles)
    mkdir('./small')
    for file in smallFiles:
        src = f'{path}/{file}'
        dist = f'{path}/small/{file}'
        copy2(src, dist)
else:
    print("There are no files in this directory")
