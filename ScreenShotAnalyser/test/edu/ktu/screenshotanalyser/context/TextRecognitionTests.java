package edu.ktu.screenshotanalyser.context;

import static org.junit.Assert.*;

import java.util.List;
import java.util.Properties;

import org.junit.Test;

import edu.stanford.nlp.ling.CoreAnnotations.PartOfSpeechAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TokensAnnotation;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;
import net.sf.extjwnl.JWNLException;
import net.sf.extjwnl.data.IndexWord;
import net.sf.extjwnl.data.POS;
import net.sf.extjwnl.data.PointerType;
import net.sf.extjwnl.data.Synset;
import net.sf.extjwnl.data.Word;
import net.sf.extjwnl.data.relationship.Relationship;
import net.sf.extjwnl.data.relationship.RelationshipFinder;
import net.sf.extjwnl.data.relationship.RelationshipList;
import net.sf.extjwnl.dictionary.Dictionary;

public class TextRecognitionTests {

	@Test
	public void testExtractNouns() {
		Properties props = new Properties();
		// "tokenize,ssplit,pos,lemma,ner,parse,dcoref"
		props.put("annotators", "tokenize,ssplit,pos");
		edu.stanford.nlp.pipeline.StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
		Annotation document = new Annotation("I am going home. Tom is not.");
		pipeline.annotate(document);

		List<CoreMap> sentences = document.get(SentencesAnnotation.class);
		String[] sentenceList = new String[sentences.size()];

		for (int i = 0; i < sentenceList.length; i++) {
			CoreMap sentence = sentences.get(i);
			System.out.println(sentence.toString());
			sentence.get(TokensAnnotation.class).stream().forEach((tok) -> {
				String PosTagg = tok.get(PartOfSpeechAnnotation.class);

				System.out.println(PosTagg + " " + tok.originalText());
			});
			sentenceList[i] = sentence.toString();
		}

	}

	@Test
	public void testGetSynonyms() throws JWNLException, CloneNotSupportedException {
		Dictionary dictionary = Dictionary.getDefaultResourceInstance();
		IndexWord ACCOMPLISH = dictionary.getIndexWord(POS.VERB, "accomplish");
		IndexWord ACCOMPLISH2 = dictionary.getIndexWord(POS.VERB, "execute");
		RelationshipList list = RelationshipFinder.findRelationships(ACCOMPLISH.getSenses().get(0),
				ACCOMPLISH2.getSenses().get(0), PointerType.VERB_GROUP);

		for (Synset s : ACCOMPLISH.getSenses()) {
			System.out.println(s);

			for (Word w : s.getWords()) {
				System.out.println(w.getLemma());
			}
		}

	}

}
