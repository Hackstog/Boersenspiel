/**
 * ShareDepositAccount.java
 * Autor:     Daniel Heigl <daniel.heigl@hs-augsburg.de>
 * Datum:     29.05.2014
 * Version:   1.2
 */


package Assets;

/**
 * Aktiendepot eines Spieler als Verkettete Liste von Aktienpaketen
 */
public class ShareDepositAccount extends Asset{
    private Node header = null;
    private Node next = null;
    
    /**
     * Fügt ein ShareItem-Element in die Liste ein
     * @param paket: Aktienpaket als ShareItem
     */
    public void insert(ShareItem paket){
        Node node = new Node(paket);
        if(header == null){
            next = node;
            header = node;
        }else{
            next.addElement(node);
            next = node;
        }
    }
    
    /**
     * Durchsucht die Liste nach einem Element und liefert eine Referenz darauf zurück
     * Wird das Element nicht gefunden wird 'null' zurückgeliefert
     * @param shareName: Name der Aktie als String
     * @return AktienPaket ShareItem
     */
    public ShareItem search(String shareName){
        Node start = header;
        while(start != null){
            if(shareName.equals(start.getElement().getName())){
                return start.getElement();
            }
            start = start.nextNode();
        }
        return null;
    }
    
    /**
     * Entfernt ein ShareItem-Element aus der Liste
     * @param paket: Aktienpaket als ShareItem
     */
    public void remove(ShareItem paket){
        Node start = header;
        Node before = null;
        while(start != null){
            if(paket.equals(start.getElement())){
                start.removeElement(before);
            }
            before = start;
            start = start.nextNode();
        }
    }
    
    
    /**
     * Gibt den Gesamtwert des Depots zurück
     * @return Wert als long
     */
    @Override
    public long getWert(){
        long i = 0;
        Node n = header;
        while(n != null){
            i += n.getElement().getWert();
            n = n.nextNode();
        }
        return i;
    }
    
    /**
     * Gibt die Listenelemente als String zurück
     * @return Alle Elemente als String 
     */
    @Override
    public String toString(){
        String result = "";
        Node n = header;
        while(n != null){
            result += n.getElement()+"<br >\n";
            n = n.nextNode();
        }
        return result;
    }

    /**
     * Innere Klasse
     * Stellt die Methoden zur Listenverwaltung zur Verfügung
     */
    private class Node{
        private Node next = null;
        private final ShareItem element;
        
        /**
         * Initialisiert den Knoten
         * @param element 
         */
        public Node(ShareItem element){
            this.element = element;
        }
        
        /**
         * Fügt ein Element an die Liste an
         * @param nächstes Element 
         */
        public void addElement(Node next){
            this.next = next;
        }
        
        /**
         * Gibt die Referenz auf den nächsten Knoten zurück
         * @return Knoten
         */
        public Node nextNode(){
            return next;
        }
        
        /**
         * Gibt das Element im aktuellen Knoten zurück
         * @return 
         */
        public ShareItem getElement(){
            return element;
        }
        
        /**
         * Entfernt ein Element aus der Liste
         * @param before 
         */
        public void removeElement(Node before){
            if(before==null){
                header = next;
            }else{
                before.next = next;
            }
        }
    }
}
