# import requests     # aiohttp로 대체
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


async def get_credit(c_id, _type):
    try:
        url = "http://api.themoviedb.org/3/" + str(_type) + '/' + str(c_id[2:]) + "/credits?api_key=315914a9c98a6d7345c3dc4ec25aa2c4&language=ko-KR"
        async with aiohttp.ClientSession() as session:
            async with session.get(url) as response:
                credits_json = json.loads(await response.text())
                for cast in credits_json['cast']:
                    if cast['popularity'] > 3.0 and cast['order'] <= 20:
                        credit_id = cast['credit_id']
                        p_id = cast['id']
                        job = 'Actor'
                        role = cast['character']
                        try:
                            cur = conn.cursor()
                            use_db_query = "use oosooDB"
                            insert_query = "INSERT INTO contents_credits(id, c_id, p_id, job, role) VALUES(?, ?, ?, ?, ?) "

                            cur.execute(use_db_query)
                            cur.execute(insert_query, (credit_id, c_id, p_id, job, role))
                            conn.commit()
                            print('id=' + str(credit_id) + ' add')

                        except mariadb.Error as e:
                            print(f"Error: {e}")
                            print(c_id)
                for crew in credits_json['crew']:
                    if crew['popularity'] > 3.0 and crew['job'] == 'Director':
                        credit_id = crew['credit_id']
                        p_id = crew['id']
                        job = 'Director'
                        try:
                            cur = conn.cursor()
                            use_db_query = "use oosooDB"
                            insert_query = "INSERT INTO contents_credits(id, c_id, p_id, job) VALUES(?, ?, ?, ?) "

                            cur.execute(use_db_query)
                            cur.execute(insert_query, (credit_id, c_id, p_id, job))
                            conn.commit()
                            print('id=' + str(credit_id) + ' add')

                        except mariadb.Error as e:
                            print(f"Error: {e}")
                            print(c_id)
    except Exception as e:
        print(str(c_id) + ' except ', e)


asyncio.set_event_loop_policy(asyncio.WindowsSelectorEventLoopPolicy())

start = time.time()

try:
    cur = conn.cursor()
    use_db_query = "use oosooDB"
    select_query = "SELECT id, _type FROM contents"

    cur.execute(use_db_query)
    cur.execute(select_query)

    contents = cur.fetchall()

    count = 0
    for c in contents:
        print(count)
        print(c[0])
        count += 1

    # m_1726 = 3051 (아이언맨)
    # latest = 35395
    # 0 ~ 35300
    for i in range(0, 353):
        tasks = [get_credit(contents[k][0], contents[k][1]) for k in range(i*100, i*100+100)]
        asyncio.run(asyncio.wait(tasks))

    tasks = [get_credit(contents[k][0], contents[k][1]) for k in range(35300, 35396)]
    asyncio.run(asyncio.wait(tasks))

except Exception as e:
    print('Exception : ', e)

end = time.time()
print(f'async : {end - start}')
