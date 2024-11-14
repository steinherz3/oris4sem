package ru.itis.secondsemwork.repository.criteria;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.stereotype.Repository;
import ru.itis.secondsemwork.model.Message;

import java.util.List;

@Repository
public class MessageRepositoryCustomImpl implements MessageRepositoryCustom {

    private final EntityManager entityManager;

    public MessageRepositoryCustomImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<Message> findByChatEmpty() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Message> cq = cb.createQuery(Message.class);
        Root<Message> root = cq.from(Message.class);

        // Создаем условие для поиска сообщений с пустым chat
        Predicate condition = cb.isNull(root.get("chat"));

        // Добавляем условие к запросу
        cq.select(root).where(condition);

        return entityManager.createQuery(cq).getResultList();
    }
}