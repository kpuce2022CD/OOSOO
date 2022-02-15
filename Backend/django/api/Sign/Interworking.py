import mariadb
import sys

def Interworking(u_id, platform, id, passwd, profile_name):
    try:
        conn = mariadb.connect(
            user="jinho",
            password="2017150011",
            host="oosoords.cqo7bp08tx6h.ap-northeast-2.rds.amazonaws.com",
            port=3306
        )

        cur = conn.cursor()
        use_db_query = "use oosooDB"
        insert_query = "INSERT INTO user_interworking(i_id, u_id, platform, id, passwd, profile_name)" \
                       "VALUES(?, ?, ?, ?, ?, ?) "

        i_id = str(u_id) + "_" + platform

        cur.execute(use_db_query)
        cur.execute(insert_query, (i_id, u_id, platform, id, passwd, profile_name))
        conn.commit()

        result = True

    except mariadb.Error as e:
        print(f"failed: Error connecting to Mariadb: {e}")
        sys.exit(1)
        result = False

    conn.close()

    return result
