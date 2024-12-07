from docx import Document


def main():
	doc = Document("ATmega_memory.docx")

	table = doc.tables[0]

	data = {}
	headers = [cell.text for cell in table.columns[0].cells[1:]]
	for row in table.columns:
		if row.cells[0].text == "ATmega328":
			for i in range(len(headers)):
				data[headers[i]] = row.cells[i + 1].text
	print(data)


if __name__ == "__main__":
	main()
