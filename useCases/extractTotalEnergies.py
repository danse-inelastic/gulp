qeOutput = 'scf3.300K.out'
import os
os.system("grep '!    total energy' "+qeOutput+" > temp")
f = file('temp')
lines = f.readlines()
fenergies = file('energies.txt','w')
rydbergToEv = 13.605698066
for line in lines:
    parts = line.split()
    fenergies.write(str(float(parts[4])*rydbergToEv)+'\n')
os.system('rm temp') 
    