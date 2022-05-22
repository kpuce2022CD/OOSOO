import requests
import mariadb
import json
import sys

# Connect to MariaDB Platform
try:
    conn = mariadb.connect(
        user="jinho",
        password="2017150011",
        host="oosoords.cqo7bp08tx6h.ap-northeast-2.rds.amazonaws.com",
        port=3306
    )

except mariadb.Error as e:
    print(f"failed: Error connecting to Mariadb: {e}")
    sys.exit(1)

try:
    cur = conn.cursor()
    use_db_query = "use oosooDB"
    select_query = f"SELECT id FROM contents WHERE _type = 'movie'"

    cur.execute(use_db_query)
    cur.execute(select_query)

    result = cur.fetchall()
    result_list = list()

    for id in result:

        c_id = str(id[0])
        slice_id = c_id[2:]
        result_list.append(slice_id)

except mariadb.Error as e:
    print(f"failed: Error connecting to Mariadb: {e}")
    sys.exit(1)
    conn.close()

for i in result_list:
    try:
        # 영화 link 가져오기
        url = "https://api.themoviedb.org/3/movie/{}/watch/providers?api_key=315914a9c98a6d7345c3dc4ec25aa2c4".format(i)
        response = requests.get(url)
        movie_link_json = json.loads(response.text)
        movie_link_result = movie_link_json["results"]
        a = movie_link_result["KR"]
        b = a['link']
        try:
            update_query = f"UPDATE contents set link='{b}' where id = 'm_{i}'"

            cur.execute(use_db_query)
            cur.execute(update_query)
            conn.commit()
        except mariadb.Error as e:
            print(f"Error: {e}")
            print(i)

    except:
        pass
conn.close()




