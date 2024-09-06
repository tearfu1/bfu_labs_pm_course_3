import argparse
from os import listdir, path as ospath


def printArray(arr):
    for elem in arr:
        print(elem)


parser = argparse.ArgumentParser()
parser.add_argument('--dirpath', default='.')
parser.add_argument('--files', nargs='*', default='')

namespace = parser.parse_args()
path = namespace.dirpath
files = namespace.files
folderInsides = set(listdir(path))
if files:
    with open('in.txt', 'w') as inFile, open('out.txt', 'w') as outFile:
        filesInside = []
        filesOutside = []
        for file in files:
            pathToFile = f'{path}/{file}'
            if ospath.isfile(pathToFile):
                filesInside.append(file)
                inFile.write(f'{file}\n')
            else:
                filesOutside.append(file)
                outFile.write(f'{file}\n')
        print('Files inside dir:')
        printArray(filesInside)
        print('Files outside dir:')
        printArray(filesOutside)
else:
    folderInsides = listdir(path)
    sumSize = 0
    fileCnt = 0
    for inside in folderInsides:
        pathToFile = f'{path}/{inside}'
        fileSize = ospath.getsize(pathToFile)
        if fileSize > 0:
            fileCnt += 1
            sumSize += fileSize
    print(f'No files entered, so basic info:\n files in dir: {fileCnt}\n total size of them: {sumSize}')