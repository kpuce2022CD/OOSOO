from api.User.LoadInterworking import load_interworking
from api.models import WishList
from api.models import Contents


def wishlist(email):
    contents = list()

    interworking = load_interworking(email)

    for user in interworking:
        print(user.i_id)
        wishes = WishList.objects.filter(i_id=user.i_id)

        print(wishes.values())

        for wish in wishes:
            print(wish.c_id)
            content = Contents.objects.filter(id=wish.c_id.id).values()[0]
            contents.append(content)

    return contents
