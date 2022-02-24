import mariadb
import sys
from django.db import connection

def signUp(email, pwd, name, phone_num, nickname, gender, birthday, job, overview):
    try:
        cur = connection.cursor()
        use_db_query = "use oosooDB"
        insert_query = "INSERT INTO users(email, passwd, name, phone_number, " \
                       "nickname, gender, birthday, job, overview) " \
                       "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?) "

        cur.execute(use_db_query)
        cur.execute(insert_query, (email, pwd, name, phone_num, nickname, gender, birthday, job, overview))
        connection.commit()

        select_user_id_query = f"SELECT id FROM users WHERE email = '{email}' AND passwd = '{pwd}'"

        cur.execute(use_db_query)
        cur.execute(select_user_id_query)
        result_set = cur.fetchall()

        for id in result_set:
            DB_id = id[0]
            if DB_id is None:
                result = -1
                break
            else:
                result = DB_id

    except mariadb.Error as e:
        print(f"failed: Error connecting to Mariadb: {e}")
        sys.exit(1)
        result = -1

    connection.close()

    return result



