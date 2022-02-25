import mariadb
import sys
from django.db import connection

def Interworking(u_email, platform, id, passwd, profile_name):
    try:

        cur = connection.cursor()
        use_db_query = "use oosooDB"
        insert_query = "INSERT INTO user_interworking(i_id, u_email, platform, id, passwd, profile_name)" \
                       "VALUES(%s, %s, %s, %s, %s, %s) "

        i_id = u_email + "_" + platform

        cur.execute(use_db_query)
        cur.execute(insert_query, (i_id, u_id, platform, id, passwd, profile_name))
        connection.commit()

        result = True

    except mariadb.Error as e:
        print(f"failed: Error connecting to Mariadb: {e}")
        sys.exit(1)
        result = False

    connection.close()

    return result
