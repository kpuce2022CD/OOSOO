from api.models import Contents


def search_test():    # 검색 테스트용 함수
    result = Contents.objects.filter(vote_count__gt=10000)
    return result
