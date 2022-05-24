import requests     # aiohttp로 대체
import aiohttp
import json
import sys
import time
import asyncio
from threading import Thread

data = []
ids = []


async def get_people(num):
    try:
        url = "https://api.themoviedb.org/3/person/" + str(
            num) + "?api_key=315914a9c98a6d7345c3dc4ec25aa2c4&language=ko-KR"
        async with aiohttp.ClientSession() as session:
            async with session.get(url) as response:
                people_json = json.loads(await response.text())
                if people_json['popularity'] > 2.0:
                    data.append(people_json)
                    ids.append(num)
                    print('id : ' + str(num))
                    print(people_json['name'])
                    print(people_json['birthday'])
                    print(people_json['gender'])
                    print(people_json['known_for_department'])
                    print(people_json['popularity'])
                    print(people_json['profile_path'])
                    print('\n')
    except:
        print('except \n')
        pass


asyncio.set_event_loop_policy(asyncio.WindowsSelectorEventLoopPolicy())

start = time.time()

tasks = [get_people(i) for i in range(0, 100)]
asyncio.run(asyncio.wait(tasks))

end = time.time()
print(f'async : {end - start}')

print('data length : ' + str(len(data)))
print('ids length : ' + str(len(ids)))

for people in data:
    print('data[\'id\'] : ' + str(people['id']))

for num in ids:
    print('id : ' + str(num))