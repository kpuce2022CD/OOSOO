import mariadb
import sys
from django.db import connection

def signUp(email, pwd, name, phone_num, nickname, gender, birthday, job, overview):
    try:
        cur = connection.cursor()
        use_db_query = "use oosooDB"
        insert_query = "INSERT INTO users(email, passwd, name, phone_number, " \
                       "nickname, gender, birthday, job, overview) " \
                       "VALUES(%s, %s, %s, %s, %s, %s, %s, %s, %s) "

        cur.execute(use_db_query)
        cur.execute(insert_query, (email, pwd, name, phone_num, nickname, gender, birthday, job, overview))
        connection.commit()

        result = True

    except mariadb.Error as e:
        print(f"failed: Error connecting to Mariadb: {e}")
        sys.exit(1)
        result = False

    connection.close()

    return result



