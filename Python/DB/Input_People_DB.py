import requests     # aiohttp로 대체
import aiohttp
import mariadb
import json
import time
import asyncio


conn = mariadb.connect(
    user="jinho",
    password="2017150011",
    host="oosoords.cqo7bp08tx6h.ap-northeast-2.rds.amazonaws.com",
    port=3306
)


async def get_people(num):
    try:
        url = "https://api.themoviedb.org/3/person/" + str(
            num) + "?api_key=315914a9c98a6d7345c3dc4ec25aa2c4&language=ko-KR"
        async with aiohttp.ClientSession() as session:
            async with session.get(url) as response:
                people_json = json.loads(await response.text())
                if people_json['popularity'] > 2.0 \
                        and (people_json['known_for_department'] == 'Acting' or people_json['known_for_department'] == 'Directing'):
                    print('id : ' + str(people_json['id']))
                    id = people_json['id']
                    name = people_json['name']
                    birthday = people_json['birthday']
                    department = people_json['known_for_department']
                    popularity = people_json['popularity']
                    profile_path = people_json['profile_path']
                    try:
                        cur = conn.cursor()
                        use_db_query = "use oosooDB"
                        insert_query = "INSERT INTO people(id, name, birthday, department, popularity, profile_path) VALUES(?, ?, ?, ?, ?, ?) "

                        cur.execute(use_db_query)
                        cur.execute(insert_query, (id, name, birthday, department, popularity, profile_path))
                        conn.commit()
                        print('id=' + str(id) + ' add')

                    except mariadb.Error as e:
                        print(f"Error: {e}")
                        print(id)
    except Exception as e:
        print(str(num) + ' except ', e)


asyncio.set_event_loop_policy(asyncio.WindowsSelectorEventLoopPolicy())

start = time.time()


# latest = 3569716
# 0 ~ 999
for i in range(0, 10):
    tasks = [get_people(k) for k in range(i*100, i*100+100)]
    asyncio.run(asyncio.wait(tasks))
    #print('\nsleep 20sec\n')
    #time.sleep(5)

end = time.time()
print(f'async : {end - start}')
