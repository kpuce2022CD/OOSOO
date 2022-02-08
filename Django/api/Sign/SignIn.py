import mariadb
import sys

def signIn(email, pwd):

    try:
        conn = mariadb.connect(
            user="jinho",
            password="2017150011",
            host="oosoords.cqo7bp08tx6h.ap-northeast-2.rds.amazonaws.com",
            port=3306
        )

        use_db_query = "use oosooDB"
        select_user_pwd_query = f"SELECT passwd FROM users WHERE email = '{email}'"

        cur = conn.cursor()

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

    conn.close()

    return result

