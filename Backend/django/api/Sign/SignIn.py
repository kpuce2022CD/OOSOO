import mariadb
import sys
from django.db import connection

def signIn(email, pwd):
    try:
        use_db_query = "use oosooDB"
        select_user_pwd_query = f"SELECT passwd FROM users WHERE email = '{email}'"

        cur = connection.cursor()

        cur.execute(use_db_query)
        cur.execute(select_user_pwd_query)
        result_set = cur.fetchall()

        result = False

        for passwd in result_set:
            DB_pwd = passwd[0]

            if DB_pwd == pwd:
                result = True
                break
            else:
                result = False

    except mariadb.Error as e:
        print(f"failed: Error connecting to Mariadb: {e}")
        sys.exit(1)

    connection.close()

    return result

