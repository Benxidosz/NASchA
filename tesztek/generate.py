out = open("test.sh", "w")
for i in range(6):
    fileName = "teszt" + str(i) + ".txt"
    out.write("java Proto.Main -t -i " + fileName + " -o out" + str(i) + ".txt\n")

out.close()
