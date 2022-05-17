#!/usr/bin/python3
print ("Content-type:text/html\r\n")
print (" ")
import cgi, os, subprocess, smtplib
from datetime import datetime
import cgitb; cgitb.enable()

form = cgi.FieldStorage()

file1 = form['file1']
file2 = form['file2']

if file1.filename:
  fn=os.path.basename(file1.filename)
  open('upload/'+fn,'wb').write(file1.file.read())
  message = 'file 1 was uploaded'
  print (message)
  save_path = 'upload/'+fn

else:
  message= 'file 1 was not uploaded'
  print (message)
  exit(1);

if file2.filename:
  fn=os.path.basename(file2.filename)
  open('upload/'+fn,'wb').write(file2.file.read())
  message = 'file 2 was uploaded'
  print (message)
  save_path2 = 'upload/'+fn

else:
  message= '<br>file 2 was not uploaded'
  print (message)
  exit(1);


cmd = ['java', 'project2', file1.filename, file2.filename]
proc = subprocess.Popen(cmd, stdout=subprocess.PIPE,stderr=subprocess.PIPE,universal_newlines=True)
stdout,stderr = proc.communicate(input='')
print ("<br>" , stdout)
print ("<err>", stderr)

d1 = datetime.now()
str = d1.strftime("%d/%m/%Y %H:%M:%S")

