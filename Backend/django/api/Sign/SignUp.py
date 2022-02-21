import mariadb
import sys

def signUp(email, pwd, name, phone_num, nickname, gender, birthday, job, overview):

    try:
        conn = mariadb.connect(
            user="jinho",
            password="2017150011",
            host="oosoords.cqo7bp08tx6h.ap-northeast-2.rds.amazonaws.com",
            port=3306
        )

        cur = conn.cursor()
        use_db_query = "use oosooDB"
        insert_query = "INSERT INTO users(email, passwd, name, phone_number, " \
                       "nickname, gender, birthday, job, overview) " \
                       "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?) "

        cur.execute(use_db_query)
        cur.execute(insert_query, (email, pwd, name, phone_num, nickname, gender, birthday, job, overview))
        conn.commit()

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

    conn.close()

    return result



