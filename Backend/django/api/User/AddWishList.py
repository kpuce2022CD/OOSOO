from api.models import WishList
from api.models import Contents


def add_wishlist(wishlist, interworking):
    contents = list()

    for title in wishlist:
        content = Contents.objects.filter(title__startswith=title)

        if content.values().count() != 0:
            exist = WishList.objects.filter(c_id=content[0])
            exist = exist.filter(i_id=interworking)

            if exist.values().count() == 0:
                contents.append(content.values()[0])
                WishList.objects.create(c_id=content[0], i_id=interworking)

    return "success"
