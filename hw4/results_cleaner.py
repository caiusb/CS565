#!/usr/bin/python

import csv

with open('hw4.results', 'r') as csvfile:
	rows = csv.reader(csvfile, delimiter='\t', quotechar='|')
	rows.next() #ignoring the header
	result = ''
	for row in rows:
		result = result + row[30] + '\n' + row[32] + '\n' + row[34] + '\n'
	print result

