from rest_framework import serializers
from .models import *


class ContentsSerializer(serializers.ModelSerializer):
    class Meta:
        model = Contents        # Contents 모델 사용
        fields = '__all__'      # 모든 필드 포함


class ContentsEpisodesSerializer(serializers.ModelSerializer):
    class Meta:
        model = ContentsEpisodes    # ContentsEpisodes 모델 사용
        fields = '__all__'          # 모든 필드 포함


class ContentsReviewSerializer(serializers.ModelSerializer):
    class Meta:
        model = ContentsReview  # ContentsReview 모델 사용
        fields = '__all__'      # 모든 필드 포함


class ContentsSeasonsSerializer(serializers.ModelSerializer):
    class Meta:
        model = ContentsSeasons     # ContentsSeasons 모델 사용
        fields = '__all__'          # 모든 필드 포함


class UserInterworkingSerializer(serializers.ModelSerializer):
    class Meta:
        model = UserInterworking    # UserInterworking 모델 사용
        fields = '__all__'          # 모든 필드 포함


class UsersSerializer(serializers.ModelSerializer):
    class Meta:
        model = Users           # Users 모델 사용
        fields = '__all__'      # 모든 필드 포함


class WatchingLogSerializer(serializers.ModelSerializer):
    class Meta:
        model = WatchingLog     # WatchingLog 모델 사용
        fields = '__all__'      # 모든 필드 포함


class WishListSerializer(serializers.ModelSerializer):
    class Meta:
        model = WishList        # Wishlist 모델 사용
        fields = '__all__'      # 모든 필드 포함
