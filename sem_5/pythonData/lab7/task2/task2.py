import json

json_file = 'ex_2.json'

with open(json_file, 'r') as f:
    people = json.load(f)

people = people['people']

print("\n".join([f"{person['name']} : {person['phoneNumber']}" for person in people]))
