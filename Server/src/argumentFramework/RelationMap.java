package argumentFramework;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * 要素と要素の関係を保持するクラスです。<br>
 * このクラスは、基本的には隣接行列を表すクラスですが、String型のIDを用いて要素にアクセスします。<br>
 * まず、ユーザはこのクラスに関係を保持したいオブジェクトのIDを登録します（addElementメソッド）。<br>
 * その後、登録したオブジェクトのIDを用いて関係の有無をセットしたり(setRelationメソッド)、<br>
 * 関係の有無を確認したり(hasRelationメソッド)することができます。<br>
 * 具体的な使用方法についてはmainメソッドをご覧ください。
 *
 * @author YutakaOmido
 */
public class RelationMap implements Comparator<Map.Entry<String, Integer>> {
	/**
	 * 要素のID(Key)から、要素の隣接行列上のIndexへのマッピングを保持します。
	 */
	private HashMap<String, Integer> idMap = new HashMap<String, Integer>();
	/**
	 * 要素間に関係が存在するかどうかを保持する隣接行列です。
	 */
	private AdjacencyMatrix matrix = new AdjacencyMatrix();
	/**
	 * このRelationMap上にある要素の数を表します。<br>
	 * この値と隣接行列の大きさ（行の数、列の数）は常に等しくなります。
	 */
	private int size = 0;

	public RelationMap() {

	}

	/**
	 * この隣接行列上に新たな要素を追加します。<br>
	 * 追加した要素の連結情報にアクセスする際には、
	 * keyで指定した要素を表すID(Key)でアクセスすることになります。
	 * @param key
	 */
	public void addElement(String key) {
		if(!this.idMap.containsKey(key)){
			this.matrix.addRowAt(this.size);
			this.matrix.addColumnAt(this.size);
			this.idMap.put(key, this.size);
			this.size ++;
		}
	}

	/**
	 * 指定されたID(Key)を持つ要素をRelationMap上から削除する
	 * @param key - 削除したい要素のID(Key)
	 */
	public void removeElement(String key){
		if(this.idMap.containsKey(key)){
			int removeElementIndex = this.idMap.get(key);
			this.matrix.removeRowAt(removeElementIndex);
			this.matrix.removeColumnAt(removeElementIndex);
			this.idMap.remove(key);
			for(Map.Entry<String, Integer> entry : this.idMap.entrySet()){
				if(removeElementIndex < entry.getValue()){
					entry.setValue(entry.getValue()-1);
				}
			}
			this.size --;
		}
	}

	/**
	 * このRelationMapに登録されている要素全てのID(Key)を取り出します。<br>
	 * 全ての要素に対してなんらかの処理を行いたい場合に用います。
	 * @return 登録されている要素のIDのセット
	 */
	public Set<String> keySet(){
		return this.idMap.keySet();
	}

	/**
	 * RelationMapの値を書き換えます。<br>
	 * fromKeyで指定される要素から、toKeyで指定される要素に関係が存在するかどうかという情報を
	 * hasRelationで指定される値に変更します。
	 * @param fromKey - 関係の元となる要素のID(Key)
	 * @param toKey   - 関係の先となる要素のID(Key)
	 * @param hasRelation - 関係が存在する場合はtrue、しない場合はfalse
	 */
	public void setRelation(String fromKey, String toKey, boolean hasRelation){
		if(this.idMap.containsKey(fromKey) && this.idMap.containsKey(toKey)){
			int fromIndex = this.idMap.get(fromKey);
			int toIndex   = this.idMap.get(toKey);
			this.matrix.setValue(fromIndex, toIndex, hasRelation);
		}
	}

	/**
	 * fromKeyで指定される要素からtoKeyで指定される要素に関係が存在するかどうかを返します。<br>
	 * fromKey、toKeyのいずれかが無効な（登録されていない）IDである場合はfalseが返されます。
	 * @param fromKey - 関係の元となる要素のID(Key)
	 * @param toKey   - 関係の先となる要素のID(Key)
	 * @return - 関係が存在すればtrue、しなければfalse
	 */
	public boolean hasRelation(String fromKey, String toKey){
		if(this.idMap.containsKey(fromKey) && this.idMap.containsKey(toKey)){
			int fromIndex = this.idMap.get(fromKey);
			int toIndex   = this.idMap.get(toKey);
			return this.matrix.getValue(fromIndex, toIndex);
		}
		return false;
	}

	/**
	 * 隣接行列の文字列表現を返します。主にテスト、デバッグに用いることを想定しています。
	 * @return - 隣接行列の文字列表現
	 */
	public String toString() {
		StringBuffer sb = new StringBuffer();

		//配列のインデックス順にソート
		ArrayList<Map.Entry<String, Integer>> entries = new ArrayList<Map.Entry<String, Integer>>(this.idMap.entrySet());
		Collections.sort(entries, new RelationMap());

		//キーの最大文字数を取得
		int maxNameLength = 0;
		for(int i=0; i<entries.size(); i++){
			if(maxNameLength < entries.get(i).getKey().length()){
				maxNameLength = entries.get(i).getKey().length();
			}
		}

		//行列の列のインデックスの文字列表現を追加
		for(int i=0; i<maxNameLength+6; i++){ sb.append(" "); }
		for(int i=0; i<this.size; i++){ sb.append(i + " "); }
		sb.append("\n");

		//行のインデックスとキーを追加し、その横に隣接行列を追加
		for(int i=0; i<this.size; i++){
			sb.append(String.format("%2d:%" + maxNameLength + "s | ", i, entries.get(i).getKey()));
			for(int j=0; j<this.size; j++){
				if(this.matrix.getValue(i, j)){
					sb.append("o ");
				}else{
					sb.append("x ");
				}
			}
			sb.append("\n");
		}
		return sb.toString();
	}

	/**
	 * idMapの要素を並べ替えるための内部メソッド。<br>
	 * idMapのエントリーを昇順に並び替えるために用いることができます。<br>
	 * このメソッドはインターフェースComparatorを実装したものです。
	 * @param entry1 - idMapのエントリー
	 * @param entry2 - idMapのエントリー
	 */
	@Override
	public int compare(Entry<String, Integer> entry1, Entry<String, Integer> entry2) {
		return entry1.getValue() - entry2.getValue();
	}

	public static void main(String[] args){
		//インスタンスを作成
		RelationMap map = new RelationMap();
		//要素を追加
		map.addElement("abc");
		map.addElement("def");
		map.addElement("ghi");
		map.addElement("new Element");
		System.out.println(map.toString());
		//関係を作成する
		map.setRelation("abc", "new Element", true); //abc -> new Element
		map.setRelation("def", "abc", true);         //def -> abc
		map.setRelation("new Element", "ghi", true); //new Element -> ghi
		System.out.println(map.toString());
		//関係の存在を確認する
		System.out.println("hasRelation abc -> def : " + map.hasRelation("abc", "def"));
		System.out.println("hasRelation def -> abc : " + map.hasRelation("def", "abc"));
		//要素を削除する
		map.removeElement("def");
		System.out.println(map.toString());
		map.removeElement("ghi");
		System.out.println(map.toString());
	}
}
