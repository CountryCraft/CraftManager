package io.github.antalafilip.CraftManager.commands;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import io.github.antalafilip.CraftManager.CraftManager;
import io.github.antalafilip.CraftManager.events.TeleportEvent;
import io.github.antalafilip.CraftManager.exceptions.InvalidModifierException;
import io.github.antalafilip.CraftManager.exceptions.NeAException;
import io.github.antalafilip.CraftManager.exceptions.NoPermException;
import io.github.antalafilip.CraftManager.exceptions.NotPlayerException;
import io.github.antalafilip.CraftManager.exceptions.NullWorldException;
import io.github.antalafilip.CraftManager.exceptions.PNOException;
import io.github.antalafilip.CraftManager.exceptions.StrNotIntException;
import io.github.antalafilip.CraftManager.exceptions.TmAException;

public class TeleportCommand extends CMD {
	
	public static final String NAME = "Teleport";
	public static final String DESC = "Teleports to a player, location or a world";
	public static final String PERM = "coutnrycraft.teleport";
	public static final String USAGE = "/teleport | /tp | /cctp";
	public static final String[] SUB;
	CraftManager craft;
	Player toTP;
	Player tpTo;
	Location tpLoc;
	boolean force = false;
	boolean override = false;
	TeleportEvent event;
	
	public TeleportCommand(CraftManager craft, final CommandSender sender) {
		super(sender, NAME, DESC, PERM, SUB, USAGE);
		this.craft = craft;
	}
	
	@SuppressWarnings("deprecation")
	public void run(CommandSender sender, Command cmd, String label, String[] args) throws NeAException, TmAException, NoPermException, NotPlayerException, PNOException, InvalidModifierException, NullWorldException, StrNotIntException
	{
		if (args.length == 0) throw new NeAException();
		else if (args.length == 1) {
			if (this.hasPerm(SUB[0])) {
				if (this.isPlayer()) {
					toTP = (Player) sender;
					tpTo = Bukkit.getPlayer(args[0]);
					if (tpTo != null) event = new TeleportEvent(sender, toTP, tpTo, force, override);
					else {
						World w = Bukkit.getWorld(args[0]); if (w != null) event = new TeleportEvent(sender, toTP, w.getSpawnLocation(), force, override);
						else throw new PNOException(args[0]);
					}
				} else throw new NotPlayerException();
			} else throw new NoPermException();
		}
		else if (args.length == 2) {
			if (args[0].startsWith("-") && args[0].length() <= 3) {
				if (args[0].contains("o")) override = true;
				if (args[0].contains("f")) {
					if (this.hasPerm(SUB[6])) force = true;
					else throw new InvalidModifierException(args[0]);
				}
				if (args[0].contains("w")) {
					if (this.isPlayer()) {
						if (this.hasPerm(SUB[2])) {
							toTP = (Player) sender;
							World w = Bukkit.getWorld(args[1]); if (w != null) event = new TeleportEvent(sender, toTP, w.getSpawnLocation(), force, override);
							else throw new NullWorldException(args[1]);
						} else throw new NoPermException();
					} else throw new NotPlayerException();
				}
				else if (override || force) {
					if (this.isPlayer()) {
						toTP = (Player) sender;
						if (this.hasPerm(SUB[0])) {
							tpTo = Bukkit.getPlayer(args[1]); if (tpTo != null) event = new TeleportEvent(sender, toTP, tpTo, force, override);
						} else throw new NoPermException();
					} else throw new NotPlayerException();
				}
			}
			else if (this.hasPerm(SUB[3])) {
				toTP = Bukkit.getPlayer(args[0]);
				tpTo = Bukkit.getPlayer(args[1]);
				if (toTP != null) {
					if (tpTo != null) event = new TeleportEvent(sender, toTP, tpTo, force, override);
					else {
						World w = Bukkit.getWorld(args[1]);
						if (w != null) event = new TeleportEvent(sender, toTP, w.getSpawnLocation(), force, override);
						else throw new PNOException(args[1]);
					}
				} else throw new PNOException(args[0]);
			} else throw new NoPermException();
		}
		else if (args.length == 3) {
			if (args[0].startsWith("-") && args[0].length() <= 3) {
				try {
					int x = Integer.parseInt(args[0]);
					if (this.hasPerm()) {
						if (this.isPlayer()) {
							toTP = (Player) sender;
							try {
								int y = Integer.parseInt(args[1]);
								try {
									int z = Integer.parseInt(args[2]);
									tpLoc = new Location(toTP.getWorld(), x , y, z);
									event = new TeleportEvent(sender, toTP, tpLoc, force, override);
									return;
								}
								catch (NumberFormatException e) {
									throw new StrNotIntException(args[2]); 
								}
							} catch (NumberFormatException e) {
								throw new StrNotIntException(args[1]);
							}
						} else throw new NotPlayerException();
					} else throw new NoPermException();
				} catch (NumberFormatException e) {
					if (args[0].contains("o")) override = true;
					if (args[0].contains("f")) {
						if (this.hasPerm(SUB[6])) force = true;
						else throw new InvalidModifierException(args[0]);
					}
					if (args[0].contains("w")) {
						if (this.hasPerm(SUB[5])) {
							toTP = Bukkit.getPlayer(args[3]);
							if (toTP != null) {
								World w = Bukkit.getWorld(args[2]); if (w != null) event = new TeleportEvent(sender, toTP, w.getSpawnLocation(), force, override);
								else throw new NullWorldException(args[2]);
								return;
							} else throw new PNOException(args[3]);
						} else throw new NoPermException();
					}
					else if (!args[0].contains("w") || !args[0].contains("o") || !args[0].contains("f")) throw new InvalidModifierException(args[0]);
					
					if (override || force && !args[0].contains("w")) {
						if (this.hasPerm(SUB[3])) {
							toTP = Bukkit.getPlayer(args[1]); if (toTP != null) {
								tpTo = Bukkit.getPlayer(args[2]); if (tpTo != null) event = new TeleportEvent(sender, toTP, tpTo, force, override);
								else throw new PNOException(args[2]);
								return;
							} else throw new PNOException(args[1]);
						} else throw new NoPermException();
					}
				}
			} 
			else if (args[0].startsWith("-") && args.length > 3) {
				try {
					int x = Integer.parseInt(args[0]);
					if (this.hasPerm(SUB[1])) {
						if (this.isPlayer()) {
							toTP = (Player) sender;
							try {
								int y = Integer.parseInt(args[1]);
								try {
									int z = Integer.parseInt(args[2]);
									tpLoc = new Location(toTP.getWorld(), x , y, z);
									event = new TeleportEvent(sender, toTP, tpLoc, force, override);
									return;
								}
								catch (NumberFormatException e) {
									throw new StrNotIntException(args[2]); 
								}
							} catch (NumberFormatException e) {
								throw new StrNotIntException(args[1]);
							}
						} else throw new NotPlayerException();
					} else throw new NoPermException();
				} catch (NumberFormatException e) {
					throw new InvalidModifierException(args[0]);
				}
			}
			try {
				int x = Integer.parseInt(args[0]);
				if (this.hasPerm(SUB[1])) {
					if (this.isPlayer()) {
						toTP = (Player) sender;
						try {
							int y = Integer.parseInt(args[1]);
							try {
								int z = Integer.parseInt(args[2]);
								tpLoc = new Location(toTP.getWorld(), x , y, z);
								event = new TeleportEvent(sender, toTP, tpLoc, force, override);
							}
							catch (NumberFormatException e) {
								throw new StrNotIntException(args[2]); 
							}
						} catch (NumberFormatException e) {
							throw new StrNotIntException(args[1]);
						}
					} else throw new NotPlayerException();
				} else throw new NoPermException();
			} catch (NumberFormatException e) {
				throw new StrNotIntException(args[0]);
			}
		}
		else if (args.length == 4) {
			if (args[0].startsWith("-") && args[0].length() <= 2) {
				if (args[0].contains("o")) override = true;
				if (args[0].contains("f")) {
					if (this.hasPerm(SUB[6])) force = true;
					else throw new InvalidModifierException(args[0]);
				}
				else if (!override) throw new InvalidModifierException(args[0]);
				if (override || force) {
					if (this.hasPerm(SUB[1])) {
						if (this.isPlayer()) {
							toTP = (Player) sender;
							try {
								int x = Integer.parseInt(args[1]);
								try {
									int y = Integer.parseInt(args[2]);
									try {
										int z = Integer.parseInt(args[3]);
										tpLoc = new Location(toTP.getWorld(), x, y, z);
										event = new TeleportEvent(sender, toTP, tpLoc, force, override);
									} catch (NumberFormatException e) {
										throw new StrNotIntException(args[3]);
									}
								} catch (NumberFormatException e) {
									throw new StrNotIntException(args[2]);
								}
							} catch (NumberFormatException e) {
								throw new StrNotIntException(args[1]);
							}
						} else throw new NotPlayerException();
					} else throw new NoPermException();
				}
			}
			else if (args[0].startsWith("-") && args[0].length() > 2) throw new InvalidModifierException(args[0]);
			else {
				toTP = Bukkit.getPlayer(args[0]);
				if (toTP != null) {
					if (this.hasPerm(SUB[4])) {
						try {
							int x = Integer.parseInt(args[1]);
							try {
								int y = Integer.parseInt(args[2]);
								try {
									int z = Integer.parseInt(args[3]);
									tpLoc = new Location(toTP.getWorld(), x, y, z);
									event = new TeleportEvent(sender, toTP, tpLoc, force, override);
								} catch (NumberFormatException e) {
									throw new StrNotIntException(args[3]);
								}
							} catch (NumberFormatException e) {
								throw new StrNotIntException(args[2]);
							}
						} catch (NumberFormatException e) {
							throw new StrNotIntException(args[1]);
						}
					} else throw new NoPermException();
				}
				else {
					World w = Bukkit.getWorld(args[0]); if (w != null) {
						if (this.hasPerm(SUB[2] + "." + w.getName())) {
							if (this.isPlayer()) {
								toTP = (Player) sender;
								try {
									int x = Integer.parseInt(args[1]);
									try {
										int y = Integer.parseInt(args[2]);
										try {
											int z = Integer.parseInt(args[3]);
											tpLoc = new Location(w, x, y, z);
											event = new TeleportEvent(sender, toTP, tpLoc, override, force);
										} catch (NumberFormatException e) {
											throw new StrNotIntException(args[3]);
										}
									} catch (NumberFormatException e) {
										throw new StrNotIntException(args[2]);
									}
								} catch (NumberFormatException e) {
									throw new StrNotIntException(args[1]);
								}
							} else throw new NotPlayerException();
						} else throw new NoPermException();
					} else throw new PNOException(args[0]);
				}
			}
		}
		else if (args.length == 5) {
			 if (args[0].startsWith("-") && args[0].length() <= 3) {
				 if (args[0].contains("o")) override = true;
				 if (args[0].contains("f")) {
					 if (this.hasPerm(SUB[6])) force = true;
					 else throw new InvalidModifierException(args[0]);
				 }
				 if (args[0].contains("w")) {
					 World w = Bukkit.getWorld(args[1]); if (w != null) {
							if (this.hasPerm(SUB[2] + "." + w.getName())) {
								if (this.isPlayer()) {
									toTP = (Player) sender;
									try {
										int x = Integer.parseInt(args[2]);
										try {
											int y = Integer.parseInt(args[3]);
											try {
												int z = Integer.parseInt(args[4]);
												tpLoc = new Location(w, x, y, z);
												event = new TeleportEvent(sender, toTP, tpLoc, override, force);
											} catch (NumberFormatException e) {
												throw new StrNotIntException(args[4]);
											}
										} catch (NumberFormatException e) {
											throw new StrNotIntException(args[3]);
										}
									} catch (NumberFormatException e) {
										throw new StrNotIntException(args[2]);
									}
								} else throw new NotPlayerException();
							} else throw new NoPermException();
						} else throw new PNOException(args[0]);
				 }
				 else if (override || force) {
						if (this.hasPerm(SUB[4])) {
							try {
								int x = Integer.parseInt(args[1]);
								try {
									int y = Integer.parseInt(args[2]);
									try {
										int z = Integer.parseInt(args[3]);
										tpLoc = new Location(toTP.getWorld(), x, y, z);
										event = new TeleportEvent(sender, toTP, tpLoc, force, override);
									} catch (NumberFormatException e) {
										throw new StrNotIntException(args[3]);
									}
								} catch (NumberFormatException e) {
									throw new StrNotIntException(args[2]);
								}
							} catch (NumberFormatException e) {
								throw new StrNotIntException(args[1]);
							}
						} else throw new NoPermException();
				 }
			 }
			 else if (args[0].startsWith("-") && args[0].length() > 3) throw new InvalidModifierException(args[0]);
			 else {
				 if (this.hasPerm(SUB[5])) {
					 World w = Bukkit.getWorld(args[1]);
					 if (w != null) {
						 if (this.hasPerm(SUB[5] + "." + w.getName())) {
							 toTP = Bukkit.getPlayer(args[0]); if (toTP != null) {
								 try {
									 int x = Integer.parseInt(args[2]);
									 try {
										 int y = Integer.parseInt(args[3]);
										 try {
											 int z = Integer.parseInt(args[4]);
											 tpLoc = new Location(w, x, y, z);
											 event = new TeleportEvent(sender, toTP, tpLoc, force, override);
										 } catch (NumberFormatException e) {
											 throw new StrNotIntException(args[2]);
										 }
									 } catch (NumberFormatException e) {
										 throw new StrNotIntException(args[2]);
									 }
								 } catch (NumberFormatException e) {
									 throw new StrNotIntException(args[2]);
								 }
							 } else throw new PNOException(args[0]);
						 } else throw new NoPermException();
					 } else throw new NullWorldException(args[1]); 
				 } else throw new NoPermException();
			 }
		}
		else if (args.length == 6) {
			if (args[0].startsWith("-") && args[0].length() <= 3) {
				if (args[0].contains("o")) override = true;
				if (args[0].contains("f")) {
					if (this.hasPerm(SUB[6])) force = true;
					else throw new InvalidModifierException(args[0]);
				}
				if (args[0].contains("w")) {
					if (this.hasPerm(SUB[5])) {
						World w = Bukkit.getWorld(args[1]); if (w != null) {
							if (this.hasPerm(SUB[5] + "." + w.getName())) {
								toTP = Bukkit.getPlayer(args[2]); if (toTP != null) {
									try {
										int x = Integer.parseInt(args[3]);
										try {
											int y = Integer.parseInt(args[4]);
											try {
												int z = Integer.parseInt(args[5]);
												tpLoc = new Location(w, x, y, z);
												event = new TeleportEvent(sender, toTP, tpLoc, force, override);
											} catch (NumberFormatException e) {
												throw new StrNotIntException(args[5]);
											}
										} catch (NumberFormatException e) {
											throw new StrNotIntException(args[4]);
										}
									} catch (NumberFormatException e) {
										throw new StrNotIntException(args[3]);
									}
								} else throw new PNOException(args[2]);
							} else throw new NoPermException();
						} else throw new NullWorldException(args[1]);
					} else throw new NoPermException();
				}
				else if (override || force) {
					if (this.hasPerm(SUB[5])) {
						World w = Bukkit.getWorld(args[2]); if (w != null) {
							if (this.hasPerm(SUB[5] + "." + w.getName())) {
								toTP = Bukkit.getPlayer(args[1]); if (toTP != null) {
									try {
										int x = Integer.parseInt(args[3]);
										try {
											int y = Integer.parseInt(args[4]);
											try {
												int z = Integer.parseInt(args[5]);
												tpLoc = new Location(w, x, y, z);
												event = new TeleportEvent(sender, toTP, tpLoc, force, override);
											} catch (NumberFormatException e) {
												throw new StrNotIntException(args[5]);
											}
										} catch (NumberFormatException e) {
											throw new StrNotIntException(args[4]);
										}
									} catch (NumberFormatException e) {
										throw new StrNotIntException(args[3]);
									}
								} else throw new PNOException(args[1]);
							} else throw new NoPermException();
						} else throw new NullWorldException(args[2]);
					} else throw new NoPermException();
				}
			}
			else if (args[0].startsWith("-") && args[0].length() > 3) throw new InvalidModifierException(args[0]);
			else throw new TmAException();
		}
		else if (args.length > 6) throw new TmAException();
		
		if (event != null) Bukkit.getPluginManager().callEvent(event);
	}
	
	static {
		SUB = new String[] { "self.player", "self.location", "self.world", "player.player", "player.location", "player.world", "force" };
	}
}
