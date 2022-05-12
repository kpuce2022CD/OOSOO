from api.models import Users

def updateUser(email, pwd, name, phone_num, nickname, gender, birthday, job, overview):

    user = Users.objects.get(email=email)
    user.passwd = pwd
    user.name = name
    user.phone_number = phone_num
    user.nickname = nickname
    user.gender = gender
    user.birthday = birthday
    user.job = job
    user.overview = overview

    user.save()

    return True