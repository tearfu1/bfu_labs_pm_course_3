import xml.etree.ElementTree as ET

tree = ET.parse('ex_3.xml', ET.XMLParser(encoding='windows-1251'))
root = tree.getroot()

for item in root.iter('СведТов'):
    item_data = item.attrib
    print(f'Наименование: {item_data["НаимТов"]}\n'
          f'Цена: {item_data["ЦенаТов"]}\n'
          f'Количество: {item_data["КолТов"]}\n'
          f'Стоимость без НДС: {item_data["СтТовБезНДС"]}\n'
          f'НДС: {item_data["НалСт"]}\n'
          f'Стоимость товара с НДС: {item_data["СтТовУчНал"]}\n')
