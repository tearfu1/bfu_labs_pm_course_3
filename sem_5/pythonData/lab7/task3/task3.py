import json

json_file = 'ex_3.json'

with open(json_file, 'r') as f:
    json_doc = json.load(f)

new_invoice = json_doc['invoices'][-1].copy()
new_invoice['id'] = 3
new_invoice['items'] = new_invoice['items'].copy()

new_items = [
    {'name': 'item 4', 'price': 50.00},
    {'name': 'item 5', 'price': 60.00}
]

new_invoice['items'].extend(new_items)
new_invoice['total'] += sum(item['price'] for item in new_items)

json_doc['invoices'].append(new_invoice)

new_json_file = 'ex_3_new.json'
with open(new_json_file, 'w') as f:
    json.dump(json_doc, f, indent=2)
