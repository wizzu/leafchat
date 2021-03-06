/*
This file is part of leafdigital leafChat.

leafChat is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

leafChat is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with leafChat. If not, see <http://www.gnu.org/licenses/>.

Copyright 2011 Samuel Marshall.
*/
package com.leafdigital.irc.api;

import util.StringUtils;
import leafchat.core.api.*;

/** Filters messages based on their source user nickname */
public class NickFilter implements MessageFilter
{
	private String nick;

	/**
	 * @param nick Nickname (not case-sensitive) from which messages are accepted;
	 *   may include wildcard *
	 */
	public NickFilter(String nick)
	{
		this.nick=nick.toLowerCase();
	}

	@Override
	public boolean accept(Msg m)
	{
		String thisNick;
		if(m instanceof UserSourceIRCMsg)
			thisNick=((UserSourceIRCMsg)m).getSourceUser().getNick();
		else if(m instanceof WatchMsg)
			thisNick=((WatchMsg)m).getNick();
		else
			return false;
		return
			StringUtils.matchWildcard(	nick,thisNick.toLowerCase());
	}

	/** Scripting filter information. */
	public static FilterInfo info=new FilterInfo(NickFilter.class,"Nickname")
	{
		@Override
		public Parameter[] getScriptingParameters()
		{
			return new Parameter[]
      {
				new Parameter(String.class,"Nickname","Wildcard * is accepted")
			};
		}
	};
}
