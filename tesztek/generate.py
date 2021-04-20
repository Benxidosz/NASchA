out = open("../out/production/NASchA/Proto/test.bat", "w")
for i in range(6):
    fileName = "teszt" + str(i + 1) + ".txt"
    out.write("java Proto.Main -t -i " + fileName + " -o out" + str(i) + ".txt\n")

out.close()
