from api.models import ContentsCredits
from api.models import People


def get_credits(c_id):
    credit_list = list()

    credits = ContentsCredits.objects.filter(c_id=c_id).values()
    for credit in credits:
        p_id = credit['p_id_id']
        print(p_id)
        people = People.objects.filter(id=p_id).values()
        for p in people:
            print(credit)
            print(p)
            credit_list.append({'credit': credit, 'people': p})

    return credit_list
