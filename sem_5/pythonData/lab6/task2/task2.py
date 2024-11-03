import xml.etree.ElementTree as ET

tree = ET.parse('ex_2_new.xml')
root = tree.getroot()

summ = 0
summ_rows = 0

for item in root.iter('Item'):
    summ_string = item.find('QNT').text.replace(',', '.')
    summ += float(summ_string)
    summ_rows += int(item.find('QNTRows').text)

summary = root.find('Summary')
summary[0].text = str(summ)
summary[1].text = str(summ_rows)
try:
    tree.write('ex_2_calculated.xml', 'UTF-8')
    print('File ex_2_calculated.xml was created')
except:
    print('Error while writing file ex_2_calculated.xml')
