with open('out.txt','r') as f:
    files = f.read().splitlines()
    for file in files:
        try:
            newFile = open(file,'x')
            newFile.close()
        except:
            pass # если есть повторяющиеся имена файлов в out.txt
    