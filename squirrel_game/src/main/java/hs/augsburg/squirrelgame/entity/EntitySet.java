package hs.augsburg.squirrelgame.entity;

import hs.augsburg.squirrelgame.main.Launcher;

import java.util.*;
import java.util.Collection;
import java.util.logging.Level;

public class EntitySet implements Collection<hs.augsburg.squirrelgame.entity.Entity> {
    /**
     * This class is used to manage the entities. Every instance of EntitySet could contain a different set of entities.
     */

    private ListElement tail;
    private ListElement head;
    /**
     * Counts the entities in the EntitySet. Do not confuse with the idCounter in Entity. The id in Entity-Class
     * is a real identifier for every entity created. The entityCounter in EntitySet identifies only counts the entities
     * in this EntitySet. So, the actual number of entities created can vary from the integer value below.
     */
    private int entityCounter = 0;

    /**
     * Calls the nextStep() method on all entities
     */
    public void nextStep(EntityContext entityContext) {
        Launcher.getLogger().log(Level.FINEST, "Called nextStep in EntitySet.");
        Iterator<Entity> entityIterator = iterator();
        while (entityIterator.hasNext()) {
            hs.augsburg.squirrelgame.entity.Entity current = entityIterator.next();
            if (current.isAlive()) {
                current.nextStep(entityContext);
            }
        }
    }


    /**
     * @return the id of the tail. When tail == null, then return -1 flag.
     */
    public int returnLastID() {
        if (tail == null) {
            return -1;
        } else {
            return tail.getEntity().getId();
        }
    }

    /**
     * @param allEntities return all entities or only alive entities?
     * @return the amount of items in the list
     */
    public int countItems(boolean allEntities) {
        ListElement temptail = tail;
        int counter = 0;
        if (temptail == null) {
            return counter;
        } else {
            while (temptail != null) {
                counter++;
                temptail = temptail.getPrevItem();
            }
        }
        return counter;
    }

    public ListElement getTail() {
        return tail;
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public boolean contains(Object o) {
        if(o instanceof Entity entity){
            int entityId = entity.getId();
            ListElement tempTail = tail;
            if (tempTail == null) {
                return false;
            }
            if (!tempTail.hasPrev() && tempTail.getEntity().getId() == entityId) {
                return true;
            }
            while (tempTail.hasPrev()) {
                ListElement newTempTail = tempTail.getPrevItem();
                if (tempTail.getEntity().getId() == entityId) {
                    return true;
                }
                tempTail = newTempTail;
            }
            return tempTail.getEntity().getId() == entityId;
        }
        return false;
    }

    @Override
    public Iterator<hs.augsburg.squirrelgame.entity.Entity> iterator() {
        Launcher.getLogger().log(Level.FINER, "Iterator call in EntitySet.");
        class IteratorRandomClass<Entity> implements Iterator<hs.augsburg.squirrelgame.entity.Entity> {
            final HashSet<Integer> usedIndex = new HashSet<>();
            boolean checkUsedIndex = false;
            ListElement currentElement;
            final ListElement[] arrayTemp = new ListElement[entityCounter];

            public IteratorRandomClass() {
                currentElement = head;
                for (int j = 0; j < entityCounter; j++) {
                    arrayTemp[j] = currentElement;
                    currentElement = currentElement.getNextItem();
                }
            }



            @Override
            public boolean hasNext() {
                return usedIndex.size() < entityCounter;
            }

            @Override
            public hs.augsburg.squirrelgame.entity.Entity next() {
                int randomIndex = 0;
                Random indexGenerator = new Random();
                if (this.hasNext()) {
                    while (!checkUsedIndex) {
                        randomIndex = indexGenerator.nextInt(entityCounter);
                        checkUsedIndex = usedIndex.add(randomIndex);
                    }
                    checkUsedIndex = false;
                    currentElement = arrayTemp[randomIndex];
                    return currentElement.getEntity();
                } else {
                    throw new NoSuchElementException("No more elements in list");
                }
            }
        }
        return new IteratorRandomClass<>();
    }




    @Override
    public Object[] toArray() {
        return new Object[0];
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return null;
    }

    @Override
    public boolean add(hs.augsburg.squirrelgame.entity.Entity entity) {
            if (contains(entity)) {
                Launcher.getLogger().log(Level.SEVERE, "IllegalStateException: An entity with this id already exists!");
                throw new IllegalStateException("An entity with this id already exists!");
            } else{
                ListElement newItem = new ListElement(entity);
                if (tail == null || head == null) {
                    head = newItem;
                    tail = newItem;
                } else {
                    ListElement prevTail = tail;
                    tail.setNextItem(newItem);
                    tail = newItem;
                    tail.setPrevItem(prevTail);
                }
                entityCounter++;
                Launcher.getLogger().log(Level.FINE, "Added new entity to EntitySet.");
                return true;
            }
    }

    @Override
    public boolean remove(Object o) {
        if(o instanceof hs.augsburg.squirrelgame.entity.Entity entity){
            if (!contains(entity)) throw new IllegalStateException("The entity doesn't exists!");
            ListElement tempTail = tail;
            if (tempTail == null) {
                return true;
            }
            if (!tempTail.hasPrev() && tempTail.getEntity() == entity) {
                tail = null;
                head = null;
                return true;
            }
            while (tempTail.hasPrev()) {
                ListElement newTempTail = tempTail.getPrevItem();
                if (tempTail.getEntity() == entity) {
                    if (tail.getEntity() == tempTail.getEntity()) {
                        tail = tempTail.getPrevItem();
                    }
                    tempTail.getPrevItem().setNextItem(null);
                    tempTail.setNextItem(null);
                    tempTail.setPrevItem(null);
                    return true;
                }
                if (tempTail.getPrevItem().getEntity() == entity) {
                    if (tempTail.getPrevItem().hasPrev()) {
                        tempTail.getPrevItem().getPrevItem().setNextItem(tempTail);
                        tempTail.setPrevItem(tempTail.getPrevItem().getPrevItem());
                        tempTail.getPrevItem().setPrevItem(null);
                        tempTail.getPrevItem().setNextItem(null);
                    } else {
                        tempTail.getPrevItem().setNextItem(null);
                        tempTail.setPrevItem(null);
                    }
                    return true;
                }
                tempTail = newTempTail;
            }
            entityCounter--;
            Launcher.getLogger().log(Level.FINE, "Removed entity from EntitySet.");
            return true;
        }
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean addAll(Collection<? extends hs.augsburg.squirrelgame.entity.Entity> c) {
        return false;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return false;
    }

    @Override
    public void clear() {
        for (Entity entity : this) {
            remove(entity);
        }
    }

    /**
     * This class must be private static!
     */
    private static class ListElement {
        private final hs.augsburg.squirrelgame.entity.Entity entity;
        private ListElement prevItem;
        private ListElement nextItem;

        public ListElement(hs.augsburg.squirrelgame.entity.Entity entity) {
            this.entity = entity;
            nextItem = null;
        }

        public hs.augsburg.squirrelgame.entity.Entity getEntity() {
            return entity;
        }

        public ListElement getPrevItem() {
            return prevItem;
        }

        public void setPrevItem(ListElement prevItem) {
            this.prevItem = prevItem;
        }

        public ListElement getNextItem() {
            return nextItem;
        }

        public void setNextItem(ListElement nextItem) {
            this.nextItem = nextItem;
        }

        public boolean hasPrev() {
            return this.prevItem != null;
        }

        public boolean hasNext() {
            return this.nextItem != null;
        }
    }

}
