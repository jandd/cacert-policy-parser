package org.cacert.policy;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.LinkedList;

import org.cacert.policy.HTMLSynthesizer.Link;

public class COD extends Entity {
	private String COD;

	private String comment;
	private String status;
	private LinkedList<Link> changes;
	private Link editor;
	public COD(String abbrev, String name, String COD, String link,
			String comment, String status, LinkedList<Link> changes, Link editor) {
		super(abbrev, name, link);
		if (!status.equals("POLICY") && !status.equals("DRAFT")) {
			throw new Error("Invalid status of COD " + abbrev + ": " + status);
		}
		this.COD = COD;
		this.comment = comment;
		this.status = status;
		this.changes = changes;
		this.editor = editor;

	}

	@Override
	public String getShortLink(String href, String hrefName) {
		return getAbbrev() + " [<a href='"
				+ HTMLSynthesizer.escape(getLink() + href) + "'>COD" + COD
				+ "</a>]" + HTMLSynthesizer.escape(hrefName);
	}
	@Override
	public String getLongLink(String href, String hrefName) {
		return getName() + " (" + getAbbrev() + " [<a href='"
				+ HTMLSynthesizer.escape(getLink())
				+ HTMLSynthesizer.escape(href) + "'>COD" + COD + "</a>]"
				+ HTMLSynthesizer.escape(hrefName) + ")";

	}
	public void printHeader(PrintWriter out) {
		out.println("<table id=\"header\"><tbody><tr>");
		out.println("<td>");
		out.println("Name: " + HTMLSynthesizer.escape(getAbbrev()) + " [COD"
				+ COD + "]<br/>");
		out.println("Status: "
				+ HTMLSynthesizer.escape(status)
				+ (changes.size() != 0 ? "&nbsp;"
						+ changes.get(changes.size() - 1) : "") + "<br/>");
		if (editor != null) {
			out.println("Editor: " + editor + "<br/>");
		}
		out.print("Changes: ");
		boolean fst = true;
		for (Link link : changes) {
			out.print((fst ? "" : ", ") + link);
			fst = false;
		}
		out.println("<br/>");
		out.println("Licence: <a href=\"https://wiki.cacert.org/Policy#Licence\""
				+ " title=\"this document is Copyright © CAcert Inc.,"
				+ " licensed openly under CC-by-sa with all disputes resolved under DRP. "
				+ "More at wiki.cacert.org/Policy\">CC-by-sa+DRP</a>");
		out.println("</td><td align=\"right\" valign=\"top\">");
		out.println("<a href=\""
				+ HTMLSynthesizer.escape(PolicyGenerator.getEntities()
						.get("PoP").getLink()) + "\">");
		out.println("  <img src=\"//cacert.org/policy/images/cacert-"
				+ status.toLowerCase()
				+ ".png\" alt=\"PoP Status - "
				+ status
				+ "\" style=\"border-style: none;\" height=\"31\" width=\"88\">");
		out.println("</a>");
		out.println("</td></tr></tbody></table>");

		emitBigTitle(out);

	}

	protected void emitBigTitle(PrintWriter out) {
		out.println("<h1>" + generateTitle()
				+ "</h1><h2>Table of Contents</h2>");
	}
	public String generateTitle() {
		return HTMLSynthesizer.escape(getName()) + " ("
				+ HTMLSynthesizer.escape(getAbbrev()) + ")";
	}

	public void emitCODIndexLines(PolicyTarget target,
			HashMap<String, String> comments) {
		target.newTableRow();
		target.emitTableCell("" + COD);
		target.emitTableCell(getAbbrev());
		target.emitTableCellLink(changes.get(0));
		target.emitTableCell(getName());
		if (editor != null) {
			target.emitTableCellLink(editor);
		} else {
			target.emitTableCell("");
		}
		target.newTableRow();
		target.emitTableCell("");
		target.emitTableCellLink(new Link(getLink(), getLink()));
		target.emitTableCellLink(changes.get(changes.size() - 1));
		String comment = comments.get(getAbbrev());
		if (comment != null) {
			target.emitTableCell(comment);
		} else {
			target.emitTableCell("");
		}
		target.emitTableCell(""); // TODO maybe colspan?

	}

	public String getId() {
		return COD;
	}
}
