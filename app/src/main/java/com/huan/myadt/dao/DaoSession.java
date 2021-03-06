package com.huan.myadt.dao;

import java.util.Map;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;
import org.greenrobot.greendao.internal.DaoConfig;

import com.huan.myadt.bean.CGQ_study.Question;
import com.huan.myadt.bean.CGQ_study.User;

import com.huan.myadt.dao.QuestionDao;
import com.huan.myadt.dao.UserDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see org.greenrobot.greendao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig questionDaoConfig;
    private final DaoConfig userDaoConfig;

    private final QuestionDao questionDao;
    private final UserDao userDao;

    public DaoSession(Database db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        questionDaoConfig = daoConfigMap.get(QuestionDao.class).clone();
        questionDaoConfig.initIdentityScope(type);

        userDaoConfig = daoConfigMap.get(UserDao.class).clone();
        userDaoConfig.initIdentityScope(type);

        questionDao = new QuestionDao(questionDaoConfig, this);
        userDao = new UserDao(userDaoConfig, this);

        registerDao(Question.class, questionDao);
        registerDao(User.class, userDao);
    }
    
    public void clear() {
        questionDaoConfig.clearIdentityScope();
        userDaoConfig.clearIdentityScope();
    }

    public QuestionDao getQuestionDao() {
        return questionDao;
    }

    public UserDao getUserDao() {
        return userDao;
    }

}
