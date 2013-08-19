#!/bin/tcsh
# note: I have ecc aliased to gcc -mcpu=v9
unlimit
echo "*********** TIMING **********"
foreach dir (*)
   if (${dir} == "timingscript") continue
   printf "%s " ${dir}
   cd ${dir}
      setenv FILES `ls *.c | grep -v .print.c`
      gcc -mcpu=v9 -O3 ${FILES}
      /usr/bin/time -p a.out < input |& grep real | awk '{ print $2 }'
   cd ..
end
