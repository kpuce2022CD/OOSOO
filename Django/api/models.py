# This is an auto-generated Django model module.
# You'll have to do the following manually to clean this up:
#   * Rearrange models' order
#   * Make sure each model has one field with primary_key=True
#   * Make sure each ForeignKey and OneToOneField has `on_delete` set to the desired behavior
#   * Remove `managed = False` lines if you wish to allow Django to create, modify, and delete the table
# Feel free to rename the models, but don't rename db_table values or field names.
from django.db import models


class Contents(models.Model):
    c_id = models.AutoField(primary_key=True)
    c_genre = models.CharField(max_length=20)
    c_name = models.CharField(max_length=100)
    c_release_date = models.DateField()
    c_cast = models.CharField(max_length=200)
    c_platform = models.CharField(max_length=100)
    c_information = models.TextField()
    c_preview_url = models.CharField(max_length=200, blank=True, null=True)
    c_number = models.IntegerField(blank=True, null=True)
    c_age_limit = models.CharField(max_length=20, blank=True, null=True)
    c_rating = models.FloatField(blank=True, null=True)

    class Meta:
        managed = False
        db_table = 'contents'


class ContentsReview(models.Model):
    cr_id = models.AutoField(primary_key=True)
    cr_c = models.ForeignKey(Contents, models.DO_NOTHING)
    cr_u = models.ForeignKey('Users', models.DO_NOTHING)
    cr_like = models.IntegerField()
    cr_rating = models.FloatField()
    cr_review = models.TextField()

    class Meta:
        managed = False
        db_table = 'contents_review'


class Interworking(models.Model):
    i_id = models.AutoField(primary_key=True)
    i_u = models.ForeignKey('Users', models.DO_NOTHING)
    i_c = models.ForeignKey(Contents, models.DO_NOTHING)
    i_ott_name = models.CharField(max_length=20)
    i_ott_id = models.CharField(max_length=20)
    i_ott_passwd = models.CharField(max_length=20)
    i_ott_profile = models.CharField(max_length=20)
    i_profile_pwd = models.CharField(max_length=20, blank=True, null=True)

    class Meta:
        managed = False
        db_table = 'interworking'


class Jjim(models.Model):
    j_id = models.AutoField(primary_key=True)
    j_c = models.ForeignKey(Contents, models.DO_NOTHING)
    j_i = models.ForeignKey(Interworking, models.DO_NOTHING)
    j_state = models.IntegerField()
    j_date = models.DateField()

    class Meta:
        managed = False
        db_table = 'jjim'


class Users(models.Model):
    u_email = models.CharField(primary_key=True, max_length=50)
    u_passwd = models.CharField(max_length=20)
    u_name = models.CharField(max_length=20)
    u_nickname = models.CharField(max_length=20)
    u_gender = models.IntegerField()
    u_age = models.IntegerField()
    u_subs = models.CharField(max_length=100, blank=True, null=True)
    u_information = models.CharField(max_length=100, blank=True, null=True)

    class Meta:
        managed = False
        db_table = 'users'


class WatchingLog(models.Model):
    w_id = models.AutoField(primary_key=True)
    w_c = models.ForeignKey(Contents, models.DO_NOTHING)
    w_i = models.ForeignKey(Interworking, models.DO_NOTHING)
    w_time = models.TimeField()

    class Meta:
        managed = False
        db_table = 'watching_log'
