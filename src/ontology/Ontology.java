package ontology;

import java.util.Set;

import org.semanticweb.HermiT.Reasoner;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.reasoner.Node;
import org.semanticweb.owlapi.reasoner.OWLReasoner;
import org.semanticweb.owlapi.reasoner.OWLReasonerFactory;

public class Ontology {

	private String url;
	private OWLOntology ontology;
	private OWLDataFactory dataFactory;
	private OWLReasoner reasoner;
	
	public Ontology(String url){
		this.url = url;
		OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
		try {
			
			ontology = manager.loadOntologyFromOntologyDocument(IRI.create(url));
			
		} catch (OWLOntologyCreationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		dataFactory = manager.getOWLDataFactory();
		OWLReasonerFactory reasonerFactory = new Reasoner.ReasonerFactory();
		reasoner = reasonerFactory.createReasoner(ontology);
	}
	
	public double getWupSimilarity(String entity1, String entity2){
		OWLClass class1 = dataFactory.getOWLClass(IRI.create(url + "#" + entity1));
		OWLClass class2 = dataFactory.getOWLClass(IRI.create(url + "#" + entity2));
		OWLClass predecessor = this.getLowestCommonPredecessorOf(class1, class2);
		double depth1 = (double) this.getDepthOf(predecessor);
		double depth2 = (double) this.getDepthOf(class1);
		double depth3 = (double) this.getDepthOf(class2);
		//System.out.println(predecessor + " depth: " + depth1);
		//System.out.println(class1 + " depth: " + depth2);
		//System.out.println(class2 + " depth: " + depth3);
		double wup = (2 * depth1) / (depth2 + depth3);
		//System.out.println("wup = " + wup);
		//System.out.println();
		return wup;
	}
	
	private OWLClass getLowestCommonPredecessorOf(OWLClass class1, OWLClass class2){
		OWLClass predecessor = null;
		Set<Node<OWLClass>> superClasses1 = reasoner.getSuperClasses(class1, true).getNodes();
		Set<Node<OWLClass>> superClasses2 = reasoner.getSuperClasses(class2, true).getNodes();
		while (true) {
			for (Node<OWLClass> cls : superClasses1){
				if (superClasses2.contains(cls)){
					if (predecessor == null){
						int d1 = this.getDepthOf(cls.getRepresentativeElement());
						int d2 = this.getDepthOf(class1);
						int d3 = this.getDepthOf(class2);
						if (d1 < d2 && d1 < d3) {
							predecessor = cls.getRepresentativeElement();
						}
					} else{
						int d1 = this.getDepthOf(cls.getRepresentativeElement());
						int d2 = this.getDepthOf(predecessor);
						int d3 = this.getDepthOf(class1);
						int d4 = this.getDepthOf(class2);
						if (d1 > d2 && d3 >= d4 && d1 < d4) {
							predecessor = cls.getRepresentativeElement();
						}
						if (d1 > d2 && d3 < d4 && d1 < d3) {
							predecessor = cls.getRepresentativeElement();
						}
					}
				}
			}
			if (predecessor != null){
				break;
			}
			Object[] nodes1 = superClasses1.toArray();
			for (Object object : nodes1){
				@SuppressWarnings("unchecked")
				Node<OWLClass> node = (Node<OWLClass>) object;
				Set<Node<OWLClass>> newSuperClasses = reasoner.getSuperClasses(node.getRepresentativeElement(), true).getNodes();
				superClasses1.addAll(newSuperClasses);
			}
			Object[] nodes2 = superClasses2.toArray();
			for (Object object : nodes2){
				@SuppressWarnings("unchecked")
				Node<OWLClass> node = (Node<OWLClass>) object;
				Set<Node<OWLClass>> newSuperClasses = reasoner.getSuperClasses(node.getRepresentativeElement(), true).getNodes();
				superClasses2.addAll(newSuperClasses);
			}
		}
		return predecessor;
	}
	
	private int getDepthOf(OWLClass cls){
		int depth = 0;
		if (cls.equals(dataFactory.getOWLThing())){
			return depth;
		}
		Set<Node<OWLClass>> superClasses = reasoner.getSuperClasses(cls, true).getNodes();
		while (true) {
			depth = depth + 1;
			for (Node<OWLClass> node : superClasses){
				if (node.getRepresentativeElement().equals(dataFactory.getOWLThing())){
					return depth;
				}
			}
			Object[] nodes = superClasses.toArray();
			for (Object object : nodes){
				@SuppressWarnings("unchecked")
				Node<OWLClass> node = (Node<OWLClass>) object;
				Set<Node<OWLClass>> newSuperClasses = reasoner.getSuperClasses(node.getRepresentativeElement(), true).getNodes();
				superClasses.addAll(newSuperClasses);
			}
		}
	}
	
}
