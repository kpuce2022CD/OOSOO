from api.models import Users
from api.models import UserInterworking
from api.models import WatchingLog
from api.models import WishList
import mariadb
import sys
from django.db import connection

def deleteUser(email):
    list_id = list()
    try:
        use_db_query = "use oosooDB"
        select_i_id = f"SELECT i_id FROM user_interworking WHERE u_email = '{email}'"

        cur = connection.cursor()
        cur.execute(use_db_query)
        cur.execute(select_i_id)
        result_set = cur.fetchall()
        for i in result_set:
            list_id.append(i)
            print(list_id)

    except mariadb.Error as e:
        print(f"failed: Error connecting to mariadb: {e}")
        sys.exit(1)

    connection.close()

    for k in list_id:
        delete_watching = WatchingLog.objects.filter(i_id=k)
        delete_watching.delete()

        delete_wish = WishList.objects.filter(i_id=k)
        delete_wish.delete()

    delete_user_inter = UserInterworking.objects.filter(u_email=email)
    delete_user_inter.delete()

    delete_user = Users.objects.get(email=email)
    delete_user.delete()

    return "Success"


